/*
 * Copyright (c) 2011, 2021 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0, which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the
 * Eclipse Public License v. 2.0 are satisfied: GNU General Public License,
 * version 2 with the GNU Classpath Exception, which is available at
 * https://www.gnu.org/software/classpath/license.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 */
package notion.api.v1.http

import java.lang.reflect.Field
import java.net.HttpURLConnection
import java.net.ProtocolException
import java.security.AccessController
import java.security.PrivilegedActionException
import java.security.PrivilegedExceptionAction

/**
 * Thanks to
 * https://github.com/eclipse-ee4j/jersey/blob/3.0.2/core-client/src/main/java/org/glassfish/jersey/client/internal/HttpUrlConnector.java#L473
 */
object HttpUrlConnPatchMethodWorkaround {

  fun setPatchRequestMethod(conn: HttpURLConnection) {
    try {
      conn.requestMethod = "PATCH" // Check whether we are running on a buggy JRE
    } catch (pe: ProtocolException) {
      try {
        AccessController.doPrivileged(
            PrivilegedExceptionAction<Any?> {
              try {
                conn.requestMethod = "PATCH"
              } catch (pe: ProtocolException) {
                var connectionClass: Class<*>? = conn.javaClass
                val delegateField: Field?
                try {
                  delegateField = connectionClass!!.getDeclaredField("delegate")
                  delegateField.isAccessible = true
                  val delegateConnection = delegateField[conn] as HttpURLConnection
                  setPatchRequestMethod(delegateConnection)
                } catch (e: NoSuchFieldException) {
                  // Ignore for now, keep going
                } catch (e: IllegalArgumentException) {
                  throw RuntimeException(e)
                } catch (e: IllegalAccessException) {
                  throw RuntimeException(e)
                }
                try {
                  var methodField: Field
                  while (connectionClass != null) {
                    try {
                      methodField = connectionClass.getDeclaredField("method")
                    } catch (e: NoSuchFieldException) {
                      connectionClass = connectionClass.superclass
                      continue
                    }
                    methodField.isAccessible = true
                    methodField[conn] = "PATCH"
                    break
                  }
                } catch (e: Exception) {
                  throw RuntimeException(e)
                }
              }
              null
            })
      } catch (e: PrivilegedActionException) {
        val cause: Throwable? = e.cause
        if (cause is RuntimeException) {
          throw cause
        } else {
          throw RuntimeException(cause)
        }
      }
    }
  }
}
