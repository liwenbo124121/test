<#ftl output_format="XML">
<project>
    <actions/>
    <description/>
    <logRotator class="hudson.tasks.LogRotator">
        <daysToKeep>3</daysToKeep>
        <numToKeep>-1</numToKeep>
        <artifactDaysToKeep>-1</artifactDaysToKeep>
        <artifactNumToKeep>-1</artifactNumToKeep>
    </logRotator>
    <keepDependencies>false</keepDependencies>
    <properties>
        <com.baidu.jenkins.plugins.job.CiJobProperty plugin="CiJobProperty@1.0.1">
            <module>${module}</module>
            <function>CODE_CHECK</function>
            <stage>CI_TESTING</stage>
            <uuid></uuid>
        </com.baidu.jenkins.plugins.job.CiJobProperty>
        <hudson.model.ParametersDefinitionProperty>
            <parameterDefinitions>
                <hudson.model.StringParameterDefinition>
                    <name>json_param</name>
                    <description/>
                    <defaultValue></defaultValue>
                </hudson.model.StringParameterDefinition>
            </parameterDefinitions>
        </hudson.model.ParametersDefinitionProperty>
        <com.baidu.jobtree.JobTreeProperty plugin="JobTree@1.0.0">
            <groupName>bugbye</groupName>
            <groupId>3478</groupId>
        </com.baidu.jobtree.JobTreeProperty>
    </properties>
    <scm class="hudson.scm.NullSCM"/>
    <assignedNode>${assignedNode}</assignedNode>
    <canRoam>false</canRoam>
    <disabled>false</disabled>
    <blockBuildWhenDownstreamBuilding>false</blockBuildWhenDownstreamBuilding>
    <blockBuildWhenUpstreamBuilding>false</blockBuildWhenUpstreamBuilding>
    <triggers/>
    <concurrentBuild>true</concurrentBuild>
    <builders>
        <hudson.tasks.Shell>
            <command>
                ${command}
            </command>
        </hudson.tasks.Shell>
    </builders>
    <publishers>
        <hudson.plugins.postbuildtask.PostbuildTask plugin="postbuild-task@1.8">
            <tasks>
                <hudson.plugins.postbuildtask.TaskProperties>
                    <logTexts>
                        <hudson.plugins.postbuildtask.LogProperties>
                            <logText>Build was aborted</logText>
                            <operator>AND</operator>
                        </hudson.plugins.postbuildtask.LogProperties>
                    </logTexts>
                    <EscalateStatus>true</EscalateStatus>
                    <RunIfJobSuccessful>false</RunIfJobSuccessful>
                    <script>
                        ${script}
                    </script>
                </hudson.plugins.postbuildtask.TaskProperties>
            </tasks>
        </hudson.plugins.postbuildtask.PostbuildTask>
    </publishers>
    <buildWrappers>
        <hudson.plugins.build__timeout.BuildTimeoutWrapper plugin="build-timeout@1.14">
            <strategy class="hudson.plugins.build_timeout.impl.AbsoluteTimeOutStrategy">
                <timeoutMinutes>720</timeoutMinutes>
            </strategy>
            <operationList>
                <hudson.plugins.build__timeout.operations.AbortOperation/>
            </operationList>
        </hudson.plugins.build__timeout.BuildTimeoutWrapper>
    </buildWrappers>
</project>