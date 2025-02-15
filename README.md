## Link-Mon

### Overview
A program written in Java for finding connected Pioneer DJ Devices on the network and displaying various information about them. It uses the [Beat-Link](https://github.com/Deep-Symmetry/beat-link) library by [DeepSymmetry](https://github.com/Deep-Symmetry/). 

### Features (for now)
- Finding CDJs on the network
- Displaying the waveform for every CDJ found
- Displaying master/sync, time, key and tempo information for every CDJ

### Features (to come)
- custom waveforms
- zooming
- waveform overview
- track information
- mixer integration for on air display
- ... (feel free to leave a feature request as an issue)


### Contributing
If anyone wants to contribute:

- you need at least java 17
- there is a maven wrapper for linux included, otherwise just get maven
- do a `mvnw clean package` and `java -jar target/link-mon...` to run the app
- all the usual stuff applies :)

### LICENSE
This project is Licensed under the MIT License. See `License.md` for details. See other Licensing in `NOTICE.md`

