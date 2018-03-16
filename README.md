Astive Toolkit (ATK) is an Open Source project sponsored by [Fonoster, Inc](https://fonoster.com). If you are interested in building communication systems using SIP you may also want to check [Sip I/O](https://github.com/psanders/sip.io), a new Sip Server built at Fonoster Inc.

# ATK: Toolkit and Application Server for Asterisk PBX

Astive Toolkit(ATK) was designed to overcome the complexity of building custom voice applications on Asterisk PBX. ATK, uses Asterisk's AGI and AMI to create powerful interactive menus with Java. Most of ATK functions can be replaced to allow third-parties implementations or services.

ATK makes creating voice applications easier for developers, however, it is important to have a good Asterisk foundation. To learn some key concepts and get up and running, check out the following tutorials:

* [Getting started with Astivlets](https://github.com/fonoster/astivetoolkit/wiki/Tutorial:-Getting-Started-With-Astivlets)
* [How to get ATK up and running](https://github.com/fonoster/astivetoolkit/wiki/Tutorial:-How-to-get-ATK-up-and-running)
* [Exploring the capabilities of the Menu API](https://github.com/fonoster/astivetoolkit/wiki/Tutorial:-Exploring-the-capabilities-of-the-Menu-API)

## Requirements

* Java 1.8+
* Maven 3.x.x+
* Asterisk 1.8+
* Git (optional)

## Getting Astive

To download and build ATK perform the following steps in your console:

```bash
git clone https://github.com/fonoster/astivetoolkit.git
cd astivetoolkit
./assembly
```

NOTE: The script will generate the Astive Server and the SDK. You can find the assemblies in folder 'dist'.

## Getting started with Astive

A good way to start building apps with Astive is by reading the [wiki](https://github.com/fonoster/astivetoolkit/wiki/GuideHelloWorldTutorial). There you will find detail information in how to build, test, and deploy your apps.

## Getting help

The best way to get help is through the [Google Groups](http://bit.ly/13KGLyx). You can also follow @astivetoolkit on Twitter for events and quick updates.

## Authors

Core team:

* [Pedro Sanders](https://github.com/psanders)
* [Eudris Cabrera](https://github.com/ecabrerar)

## Contributions
* Please see our [Contribution Documents](https://raw.githubusercontent.com/fonoster/astivetoolkit/master/CONTRIBUTORS) 
* [Contributors](https://github.com/fonoster/astivetoolkit/contributors)

### Copyright

Copyright (C) 2018 by [Fonoster, INC](https://fonoster.com). Apache License, Version 2.0 (see LICENSE for details).
