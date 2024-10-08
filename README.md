﻿# LanguageUtilsPlus

LanguageUtilsPlus is a utility library designed specifically for higher Minecraft server versions (1.19.4+). It simplifies the management and updating of language files by automatically downloading the necessary files, eliminating manual updates with each new Minecraft release.

## Features

- **Automatic Language File Download**: Automatically downloads required language files based on the server version and loaded language files.
- **100% API Compatibility with LanguageUtils**: For higher versions, simply replace the existing plugin without changing any code. Refer to [LanguageUtils API Usage](https://github.com/NyaaCat/LanguageUtils#use-language-utils) for implementation details.

## Usage

LanguageUtilsPlus maintains full API compatibility with LanguageUtils. If you're already using LanguageUtils, you can upgrade seamlessly by replacing the plugin.

## Config

```yaml
MinecraftVersion: 'auto'
FallbackLanguage: en-us
LoadLanguage:
  - en-us
  - zh-cn
# or BMCLAPI https://bmclapi2.bangbang93.com/mc/game/version_manifest_v2.json
VersionManifestUrl: 'http://launchermeta.mojang.com/mc/game/version_manifest_v2.json'
# or BMCLAPI https://bmclapi2.bangbang93.com/assets
ResourcesUrl: 'https://resources.download.minecraft.net/'
```

## Metrics collection
LanguageUtilsPlus collects anonymous server statistics through **bStats**.

If you'd like to disable metrics collection via bStats, you can edit the **plugins/bStats/config.yml** file.



## Compatibility with Lower Versions

For Minecraft server versions below 1.19.4, please use the following:

- [NyaaCat/LanguageUtils](https://github.com/NyaaCat/LanguageUtils)  <= 1.17
- [ColorsWind/LanguageUtils](https://github.com/ColorsWind/LanguageUtils) 1.18~1.20

