
//https://aws.amazon.com/blogs/developer/introducing-crt-based-s3-client-and-the-s3-transfer-manager-in-the-aws-sdk-for-java-2-x/

//> using lib "software.amazon.awssdk:aws-sdk-java:2.19.19"
//> using lib "software.amazon.awssdk.crt:aws-crt:0.21.1"

import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider
import software.amazon.awssdk.core.sync.RequestBody
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import software.amazon.awssdk.services.s3.model.PutObjectResponse
import software.amazon.awssdk.services.s3.model.S3Exception


val credentials = ProfileCredentialsProvider.create()
val region = Region.EU_NORTH_1
