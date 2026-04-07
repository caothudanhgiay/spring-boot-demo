pipeline {
    agent any

    environment {
        IMAGE_NAME = 'my-java-app'
        CONTAINER_NAME = 'my-java-app'
        PORT_MAPPING = '8081:8080'
    }

    stages {
        stage('Checkout Code') {
            steps {
                // Checkout code tu kho luu tru GitHub
                checkout scm
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    echo "Building Docker Image: ${IMAGE_NAME}:latest..."
                    sh 'docker build -t ${IMAGE_NAME}:latest .'
                }
            }
        }

        stage('Deploy Container') {
            steps {
                script {
                    echo "Dung va xoa container cu (neu co)..."
                    sh 'docker stop ${CONTAINER_NAME} || true'
                    sh 'docker rm ${CONTAINER_NAME} || true'

                    echo "Khoi tao container moi voi cac credentials lay tu Jenkins..."
                    withCredentials([
                        string(credentialsId: 'DB_HOST', variable: 'db_host'),
                        string(credentialsId: 'DB_NAME', variable: 'db_name'),
                        string(credentialsId: 'DB_USERNAME', variable: 'db_user'),
                        string(credentialsId: 'DB_PASSWORD', variable: 'db_pass')
                    ]) {
                        sh """
                        docker run -d --name ${CONTAINER_NAME} \
                          -p ${PORT_MAPPING} \
                          -e SPRING_DATASOURCE_URL=jdbc:mysql://${db_host}:3306/${db_name}?useSSL=false \
                          -e SPRING_DATASOURCE_USERNAME=${db_user} \
                          -e SPRING_DATASOURCE_PASSWORD='${db_pass}' \
                          -e SPRING_JPA_HIBERNATE_DDL_AUTO=update \
                          ${IMAGE_NAME}:latest
                        """
                    }
                }
            }
        }
    }

    post {
        success {
            echo "🔥 Trien khai THANH CONG! Truy cap tai http://98.94.65.176:8081"
        }
        failure {
            echo "❌ Trien khai THAT BAI, ban hay xem log nhe!"
        }
    }
}
