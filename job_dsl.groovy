folder('Tools') {
    description('Folder for miscellaneous tools.')
}
freeStyleJob('Tools/SEED') {
    parameters {
        stringParam('GITHUB_NAME', '', 'GitHub repository owner/repo_name (e.g.: "EpitechIT31000/chocolatine")')
        stringParam('DISPLAY_NAME', '', 'Display name of the job')
    }
    steps {
        dsl {
            text (
                '''
                freeStyleJob("\${DISPLAY_NAME}") {
                    wrappers {
                        preBuildCleanup()
                    }
                    properties {
                        githubProjectUrl("\${GITHUB_NAME}")
                    }
                    scm {
                        triggers {
                            cron('* * * * *')
                        }
                        git {
                            remote {
                                url("\${GITHUB_NAME}")
                            }
                            branch("*")
                        }
                    }

                    steps {
                        shell('make fclean')
                        shell('make')
                        shell('make tests_run')
                        shell('make clean')
                    }
                }
                '''
            )
        }
    }
}
