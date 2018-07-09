#!/bin/sh

# Copyright (C) 2017 by Fonoster Inc (http://fonoster.com)
# http://astivetoolkit.org
#
# This file is part of Astive Toolkit(ATK)
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with
# the License. You may obtain a copy of the License at
#
#      http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

command -v java >/dev/null 2>&1 || { echo "I require Java but it's not installed. Aborting." >&2; exit 1; }

# Might fail if $0 is a link
export ASTIVE_HOME=`dirname "$0"`/..

# Remove this comment for other than english
#OPTIONS="-Duser.language=es -Duser.country=DO"

java $OPTIONS -classpath $ASTIVE_HOME/lib/atk-commons-1.* com.fonoster.astive.Version
