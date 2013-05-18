/* 
 * Copyright (C) 2010-2012 PhonyTive LLC
 * http://astive.phonytive.com
 *
 * This file is part of Astive Toolkit
 *
 * Astive is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Astive is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Astive.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.astivetoolkit.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;



/**
 * Utility to copy a file from one location to another.
 *
 * @since 1.0.0
 */
public final class CopyFile {
  private CopyFile() {
  }

  /**
   * Copy a file from one location to another.
   *
   * @param srFile source file
   * @param dtFile destination file
   *
   * @throws FileNotFoundException if source file is not found.
   * @throws IOException if can't perform a I/O operation.
   */
  public static synchronized void copyfile(String srFile, String dtFile)
                                    throws FileNotFoundException, IOException {
    try {
    final  File f1 = new File(srFile);
    final  File f2 = new File(dtFile);
    final  InputStream in = new FileInputStream(f1);

    final  OutputStream out = new FileOutputStream(f2);

      byte[] buf = new byte[0x400];
      int len;

      while ((len = in.read(buf)) > 0x0) {
        out.write(buf, 0x0, len);
      }

      in.close();
      out.close();
    } catch (FileNotFoundException ex) {
      throw new FileNotFoundException();
    } catch (IOException e) {
      throw new IOException();
    }
  }
}
