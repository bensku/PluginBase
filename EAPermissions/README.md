# EAPermissions

### Installation
1. Remove any other permission or chat systems
2. Drop EAPermissions.jar into /plugins/
3. Start the server
4. EAPermissions will automatically convert from PermissionsEx or GroupManager

### Integration
1. Essentials
  - Essentials doesn't currently support EAPerms (although drtshock's [EssentialsX](https://ci.drtshock.net/job/EssentialsX/) does)
  - Remove the ````player-commands```` section of the Essentials config.yml
  - Remove EssentialsChat (if installed)

### Command Structure
1. Information
  - /eaperms group <group> list users - List users in a group
  - /eaperms listgroups - List available groups
2. Player Group Management
  - /eaperms user <user> set group <group> - Sets a player's main group
  - /eaperms user <user> add subgroup <group> - Gives a player a subgroup
  - /eaperms user <user> remove subgroup <subgroup> - Removes a subgroup
  - /eaperms user <user> has group <group> - In group / subgroup
3. Permission Management
  - /eaperms user/group <user/group> add permission <permission> - Add a permission
  - /eaperms user/group <user/group> remove permission <permission> - Remove a permission
  - /eaperms user/group <user/group> has permission <group> - Permission check
  - /eaperms user/group <user/group> list permissions - List permissions
4. Option Management
  - /eaperms user/group <user/group> set prefix <prefix> - Set a prefix
  - /eaperms user <user> set suffix <suffix> - Set a suffix
  - /eaperms user/group <user/group> set option <option> <value> - Set an option
  - /eaperms user/group <user/group> has option <option> - Has an option
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