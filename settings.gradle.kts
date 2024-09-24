rootProject.name = "mission-microservice"
include("app")

plugins {
    id("org.danilopianini.gradle-pre-commit-git-hooks") version "2.0.13"
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
