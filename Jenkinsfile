node {
    def mvnHome
    stage('Checkout') {
        git branch: 'master', credentialsId: '99bd26aa-bf64-4fde-8de8-34a6c4448994', url: 'https://github.com/midianet/journey-adm'
    }
    try{
        stage 'Build'
        mvnHome = tool 'Maven 3.5.4'       
        sh "'${mvnHome}/bin/mvn' -Dmaven.test.failure.ignore -Dorg.slf4j.simpleLogger.defaultLogLevel=INFO  clean package"
    }catch(Exception e){
        failBuild()
    }
}
