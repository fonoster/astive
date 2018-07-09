/* 
 * Copyright (C) 2017 by Fonoster Inc (http://fonoster.com)
 * http://github.com/fonoster/astive
 *
 * This file is part of Astive
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.fonoster.astive.agi.command;

import java.io.Serializable;
import com.fonoster.astive.agi.annotation.AgiCommand;
import com.fonoster.astive.agi.annotation.Parameter;

/**
 * Sends the given image on a <code>channel</code>. Most channels do not support
 * the transmission of images.
 *
 * <p>Returns 0 if image is sent, or if the channel does not support image
 * transmission.  Returns -1 only on error/hangup. Image names should not
 * include extensions.
 *
 * @since 1.0
 */
@AgiCommand(command = "SEND IMAGE")
public class SendImage implements Serializable {
  private static final long serialVersionUID = -3133245749356443450L;
  @Parameter(optional = false)
  private String image;

  /**
   * Create a new SendImage object.
   *
   * @param image image to send.
   */
  public SendImage(String image) {
    this.image = image;
  }

  /**
   * Get image to send.
   *
   * @return image to to send.
   */
  public String getImage() {
    return image;
  }

  /**
   * Set image to send.
   *
   * @param image image to send.
   */
  public void setImage(String image) {
    this.image = image;
  }
}
