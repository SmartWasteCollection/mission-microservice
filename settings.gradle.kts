rootProject.name = "mission-microservice"
include("app")

plugins {
    id("org.danilopianini.gradle-pre-commit-git-hooks") version "1.1.18"
}

gitHooks {
    file(".git/hooks").mkdirs()
    commitMsg {
        conventionalCommits {
            defaultTypes()
            types("deps")
        }
    }
    preCommit {
        tasks("ktlintCheck")
    }
    createHooks()
}
