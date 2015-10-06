# Annotations
Add our framework to your plugin and shade it into it to enable this feature.

## Example Usage
```
package org.extendedalpha.test;

@Main
@Name("Test")
@Version("v1.0")
@Description("A test plugin.")
@LoadOn(PluginLoadOrder.POSTWORLD)
@Author("ExileDev")
@Website("www.extendedalpha.org")
@UsesDatabase
@DependsOn({"WorldEdit", "Towny"})
@SoftDependsOn("Vault")
@LogPrefix("Testing")
@LoadBefore("Essentials")
@Commands({
        @Cmd(
                value = "test",
                desc = "Teset command",
                aliases = {"Tester", "tes"},
                permission = "test.test",
                permissionMessage = "You do not have permission!",
                usage = "/<command> [test|stop]"
        ),
        @Cmd("bar")
})
@Permissions({
        @Perm(
                value = "test.test",
                desc = "Allows test command",
                defaultValue = PermissionDefault.OP
        ),
        @Perm(
                value = "test.*",
                desc = "Wildcard perm",
                defaultValue = PermissionDefault.OP,
                children = {"test.test"}
        )
})
public class Test extends JavaPlugin {}
```