pipeline {
    agent any

    environment {
        IMAGE_NAME = "chatapp-image"
        CONTAINER_NAME = "chatapp-container"
        PORT = "8081"
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                // Cấp quyền thực thi cho mvnw trước khi chạy
                sh 'chmod +x mvnw'
                sh './mvnw clean package -DskipTests'
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    docker.build(IMAGE_NAME)
                }
            }
        }

        stage('Run Container') {
            steps {
                script {
                    // Dừng container cũ nếu có
                    sh "docker rm -f ${CONTAINER_NAME} || true"
                    // Chạy container mới map port 8081
                    sh "docker run -d --name ${CONTAINER_NAME} -p ${PORT}:8081 ${IMAGE_NAME}"
                }
            }
        }
    }

    post {
        always {
            echo "Pipeline finished"
        }
    }
}
