# InFamousPermissions

### Installation
1. Remove any other permission or chat systems
2. Drop InFamousPermissions.jar into /plugins/
3. Start the server
4. InFamousPermissions will automatically convert from PermissionsEx or GroupManager

### Integration
1. Essentials
  - Essentials doesn't currently support InFamousPermissions (although drtshock's [EssentialsX](https://ci.drtshock.net/job/EssentialsX/) does)
  - Remove the ````player-commands```` section of the Essentials config.yml
  - Remove EssentialsChat (if installed)

### Command Structure
1. Information
  - /infamousperms group <group> list users - List users in a group
  - /infamousperms listgroups - List available groups
2. Player Group Management
  - /infamousperms user <user> set group <group> - Sets a player's main group
  - /infamousperms user <user> add subgroup <group> - Gives a player a subgroup
  - /infamousperms user <user> remove subgroup <subgroup> - Removes a subgroup
  - /infamousperms user <user> has group <group> - In group / subgroup
3. Permission Management
  - /infamousperms user/group <user/group> add permission <permission> - Add a permission
  - /infamousperms user/group <user/group> remove permission <permission> - Remove a permission
  - /infamousperms user/group <user/group> has permission <group> - Permission check
  - /infamousperms user/group <user/group> list permissions - List permissions
4. Option Management
  - /infamousperms user/group <user/group> set prefix <prefix> - Set a prefix
  - /infamousperms user <user> set suffix <suffix> - Set a suffix
  - /infamousperms user/group <user/group> set option <option> <value> - Set an option
  - /infamousperms user/group <user/group> has option <option> - Has an option
5. Prefix / Suffix Management
  - /prefix <prefix> - Change prefix
  - /prefixreset [player] - Reset prefix
  - /suffix <suffix> - Change suffix
  - /suffixreset [player] - Reset suffix
6. Options
  - Syntax: String: "[value]", Boolean: "b:[value]", Integer: "i:[value]", Double: "d:[value]"

### File Structure
1. Base Directory
  - Server Groups and Config
2. Worlds
  - Users file and Groups file