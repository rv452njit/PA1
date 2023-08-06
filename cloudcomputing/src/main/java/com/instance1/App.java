package com.instance1;

import com.amazon.sqs.javamessaging.AmazonSQSMessagingClientWrapper;
import com.amazon.sqs.javamessaging.ProviderConfiguration;
import com.amazon.sqs.javamessaging.SQSConnection;
import com.amazon.sqs.javamessaging.SQSConnectionFactory;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.auth.WebIdentityTokenCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.rekognition.model.AmazonRekognitionException;
import com.amazonaws.services.rekognition.model.DetectLabelsRequest;
import com.amazonaws.services.rekognition.model.DetectLabelsResult;
import com.amazonaws.services.ec2.AmazonEC2Client;
//import com.amazonaws.services.ec2.model.Image;
import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.amazonaws.services.rekognition.model.DetectTextRequest;
import com.amazonaws.services.rekognition.model.DetectTextResult;
import com.amazonaws.services.rekognition.model.S3Object;
import com.amazonaws.services.rekognition.model.TextDetection;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.S3ClientOptions;
import com.amazonaws.services.s3.internal.eventstreaming.Message;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.CreateBucketRequest;
import com.amazonaws.services.s3.model.ListObjectsV2Request;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.services.sqs.*;
import com.amazonaws.services.sqs.AmazonSQS;

import com.amazonaws.services.sqs.AmazonSQSClientBuilder;

import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.amazonaws.services.rekognition.model.AmazonRekognitionException;
import com.amazonaws.services.rekognition.model.Image;
import com.amazonaws.services.rekognition.model.Label;
import com.amazonaws.services.rekognition.model.S3Object;
import com.amazonaws.services.rekognition.model.DetectTextRequest;
import com.amazonaws.services.rekognition.model.DetectTextResult;
import com.amazonaws.services.rekognition.model.TextDetection;
import com.amazonaws.services.sqs.model.CreateQueueRequest;
import com.amazonaws.services.sqs.model.GetQueueAttributesResult;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.jms.JMSException;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.management.JMException;

import com.amazonaws.services.s3.S3ObjectResource;
import lombok.Value;
import lombok.val;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.SqsClientBuilder;

import java.util.List;

import jakarta.jms.TextMessage;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.HashMap;
import java.util.Map;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.CreateQueueRequest;
import com.amazonaws.services.sqs.model.MessageAttributeValue;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageRequest;

public class App {
     private static String ACCESS_KEY = "AKIA2L3FCO3X37SKDWGI";
      private static String SECRET_KEY = "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";
     
      public static void main(String[] args) throws Exception {

        try {
            AWSCredentialsProvider credentialsProvider = new AWSStaticCredentialsProvider(new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY )); 

            AmazonSQS sqs = AmazonSQSClientBuilder.standard()
            .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials (ACCESS_KEY, SECRET_KEY)))
            .withRegion(Regions.US_EAST_1)
            .build();

                CreateQueueRequest createStandardQueueRequest = new CreateQueueRequest("rv452-Q");
                String standardQueueUrl = sqs.createQueue(createStandardQueueRequest).getQueueUrl();

                Map<String, String> queueAttributes = new HashMap<>();
                queueAttributes.put("FifoQueue", "true");
                queueAttributes.put("ContentBasedDeduplication", "true");
                
                CreateQueueRequest createFifoQueueRequest = new CreateQueueRequest("rv452-Q.fifo").withAttributes(queueAttributes);
                String fifoQueueUrl = sqs.createQueue(createFifoQueueRequest).getQueueUrl();

                // Map<String, MessageAttributeValue> messageAttributes = new HashMap<>();
                // messageAttributes.put("AttributeOne", new MessageAttributeValue()
                // .withStringValue("Message from ravi")
                // .withDataType("String"));  
                    
                // SendMessageRequest sendMessageStandardQueue = new SendMessageRequest()
                // .withQueueUrl(standardQueueUrl)
                // .withMessageBody("A simple message.")
                // .withDelaySeconds(900)
                // .withMessageAttributes(messageAttributes);
                
                // sqs.sendMessage(sendMessageStandardQueue);

                // SendMessageRequest sendMessageFifoQueue = new SendMessageRequest()
                // .withQueueUrl(fifoQueueUrl)
                // .withMessageBody("Message 1")
                // .withMessageGroupId("Group-1")
                // .withMessageAttributes(messageAttributes);
                
                // sqs.sendMessage(sendMessageFifoQueue);

                //  SendMessageRequest sendMessageFifoQueue1 = new SendMessageRequest()
                // .withQueueUrl(fifoQueueUrl)
                // .withMessageBody("Message 2")
                // .withMessageGroupId("Group-2")
                // .withMessageAttributes(messageAttributes);
                
                //sqs.sendMessage(sendMessageFifoQueue1);

                ReceiveMessageRequest receiveMessageRequests = new ReceiveMessageRequest(fifoQueueUrl)
                .withWaitTimeSeconds(20)
                .withMaxNumberOfMessages(10);
              
                List<com.amazonaws.services.sqs.model.Message> sqsMessages = sqs.receiveMessage(receiveMessageRequests).getMessages();
                for(com.amazonaws.services.sqs.model.Message m: sqsMessages){
                    System.out.println(m.getBody());
                }
                sqsMessages.get(0).getAttributes();
                sqsMessages.get(0).getBody();
      }
      catch (Exception e) {
        e.printStackTrace();
      }
    }      
}

//@SpringBootApplication
// public class App {

//     private static AmazonS3 s3Client;
//     private static String bucket = "";
//         public static void main(String[] args)  throws JMSException {
       
      
//         String bucket_name = "rv452-bucket-new";
//         String region = "us-east-1";
//             Regions reg = Regions.US_EAST_1;
//        String bucketName = "rv452-bucket";
       
//         String ACCESS_KEY = "AKIA2L3FCO3X37SKDWGI";
//                 String SECRET_KEY = "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";
//           try {
//                 AWSCredentialsProvider credentialsProvider = new AWSStaticCredentialsProvider(new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY )); 
//                 AmazonS3  s3Client = AmazonS3ClientBuilder.standard().withCredentials(credentialsProvider).withRegion(Regions.US_EAST_1).build();
//                 BasicAWSCredentials awsCreds = new BasicAWSCredentials(ACCESS_KEY , SECRET_KEY );

//                 List<Bucket> buckets = s3Client.listBuckets();
//                 int bucketSize = buckets.size();

//                 if (bucketSize> 0){
//                     System.out.println("My buckets now are:");

//                     for (Bucket b : buckets) {
//                       if(b.getName().toString() == "rv452") {
//                         bucket = b.getName().toString();
//                       }
              
//                         System.out.println(bucket);
//                     }

//                     ListObjectsV2Request req = new ListObjectsV2Request().withBucketName("rv452");            
//                     ListObjectsV2Result result = s3Client.listObjectsV2(req); 
                    
//                     AmazonSQS sqsClient = AmazonSQSClientBuilder.standard()
//                     .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials (ACCESS_KEY, SECRET_KEY)))
//                     .withRegion(reg)
//                     .build();
        
//                   try {
//                      for (S3ObjectSummary objectSummary : result.getObjectSummaries()) 

//                         {    
//                         if(objectSummary.getKey().contains("car"))
//                         {

//                             // System.out.println("Received: " + ((TextMessage) message).getText());
//                             String photo = objectSummary.getKey();
//                                                                           System.out.println(photo);

//                             //text rekognition of the image from the queue
//                             DetectTextRequest request = new DetectTextRequest()
//                             .withImage(new Image()
//                             .withS3Object(new S3Object()
//                             .withName(photo)
//                             .withBucket("rv452")));
                                  			    
//                             try {
//                                 AmazonRekognition rekognitionClient = AmazonRekognitionClientBuilder.standard()
//                                 //.withCredentials(credentialsProvider)
//                               .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials (ACCESS_KEY, SECRET_KEY)))
//                                 .withRegion(reg)
//                                 .build();
                            
//                                     DetectTextResult result1 = rekognitionClient.detectText(request);
//                                     List<TextDetection> textDetections = result1.getTextDetections();
//                                     if (!textDetections.isEmpty()) {
//                                             System.out.print("Text Detected lines and words for:  " + photo + " ==> ");
//                                         for (TextDetection text: textDetections) {
                                            
//                                             System.out.print("  Text Detected: " + text.getDetectedText() + " , Confidence: " + text.getConfidence().toString());
//                                             if (text.getConfidence().intValue() > 80) {
//                                               //AWSCredentialsProvider credentialsProvider = new AWSStaticCredentialsProvider(new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY )); 

//                                                   AmazonSQS sqs = AmazonSQSClientBuilder.standard()
//                                                   .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials (ACCESS_KEY, SECRET_KEY)))
//                                                   .withRegion(Regions.US_EAST_1)
//                                                   .build();

//                                                       CreateQueueRequest createStandardQueueRequest = new CreateQueueRequest("rv452-Q");
//                                                       String standardQueueUrl = sqs.createQueue(createStandardQueueRequest).getQueueUrl();

//                                                       Map<String, String> queueAttributes = new HashMap<>();
//                                                       queueAttributes.put("FifoQueue", "true");
//                                                       queueAttributes.put("ContentBasedDeduplication", "true");
                                                      
//                                                       CreateQueueRequest createFifoQueueRequest = new CreateQueueRequest("rv452-Q.fifo").withAttributes(queueAttributes);
//                                                       String fifoQueueUrl = sqs.createQueue(createFifoQueueRequest).getQueueUrl();

//                                                       Map<String, MessageAttributeValue> messageAttributes = new HashMap<>();
//                                                       messageAttributes.put("AttributeOne", new MessageAttributeValue()
//                                                       .withStringValue(photo)
//                                                       .withDataType("String"));  
                                                          
//                                                       // SendMessageRequest sendMessageStandardQueue = new SendMessageRequest()
//                                                       // .withQueueUrl(standardQueueUrl)
//                                                       // .withMessageBody("A simple message.")
//                                                       // .withDelaySeconds(900)
//                                                       // .withMessageAttributes(messageAttributes);
                                                      
//                                                       // sqs.sendMessage(sendMessageStandardQueue);

//                                                       SendMessageRequest sendMessageFifoQueue = new SendMessageRequest()
//                                                       .withQueueUrl(fifoQueueUrl)
//                                                       .withMessageBody(photo)
//                                                       .withMessageGroupId("Group-1")
//                                                       .withMessageAttributes(messageAttributes);
                                                      
//                                                       sqs.sendMessage(sendMessageFifoQueue);

//                                                       //  SendMessageRequest sendMessageFifoQueue1 = new SendMessageRequest()
//                                                       // .withQueueUrl(fifoQueueUrl)
//                                                       // .withMessageBody("Message 2")
//                                                       // .withMessageGroupId("Group-2")
//                                                       // .withMessageAttributes(messageAttributes);
                                                      
//                                                       // sqs.sendMessage(sendMessageFifoQueue1);

//                                             } else {
//                                                System.out.println("Confidence");
//                                             }
//                                             System.out.println();
//                                             }			              
//                                     }
//                                 } catch(AmazonRekognitionException e) {
//                                     System.out.print("oops in the catch");
//                                     e.printStackTrace();
                                
//                                   }    
//                                 }
//                             }
//                           } catch (Exception e) {
//                               e.printStackTrace();
//                             }
//                         }
//         } catch (Exception e) {
//                                   e.printStackTrace();
//                                 }
// }
// }

// // @SpringBootApplication
// // class MyListener implements MessageListener {
	 
// //     @Override
// //     public void onMessage(Message message) {
    	
// //         try {
        	
// //          // The credentials added are as ## , and are active , simply execute the jar file.
// //         	BasicAWSCredentials awsCreds = new BasicAWSCredentials("#####################" , "########################" );
// //             Regions clientRegion = Regions.US_EAST_1;
// //             String bucketName = 'rv452';
            
// //             ListObjectsV2Request req = new ListObjectsV2Request().withBucketName(bucketName);            
// //             ListObjectsV2Result result;
            
// //             AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
// //                     .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
// //                     .withRegion(clientRegion)
// //                     .build();
            
// //             AmazonRekognition rekognitionClient = AmazonRekognitionClientBuilder.standard()
// //          		   .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
// //          		   .withRegion(clientRegion)
// //          		   .build();
            
// //             result = s3Client.listObjectsV2(req);    
// //             for (S3ObjectSummary objectSummary : result.getObjectSummaries()) 
// //             {    
// //                String m = (String) ((TextMessage) message).getText().toString();
// //      		   if(objectSummary.getKey().contains(m))
// //      		   {
// //      			  // System.out.println("Received: " + ((TextMessage) message).getText());
// //      			   String photo = objectSummary.getKey();
// //      			   //text rekognition of the image from the queue
// //      			   DetectTextRequest request = new DetectTextRequest()
// //      			              .withImage(new Image()
// //      			              .withS3Object(new S3Object()
// //      			              .withName(photo)
// //      			              .withBucket(bucketName)));            			    
// //      			   try {
// //      			          DetectTextResult result1 = rekognitionClient.detectText(request);
// //      			          List<TextDetection> textDetections = result1.getTextDetections();
// //      			          if (!textDetections.isEmpty()) {
// //      			        	  	System.out.print("Text Detected lines and words for:  " + photo + " ==> ");
// //      			        	  for (TextDetection text: textDetections) {
     			        		
// //      			        		 System.out.print("  Text Detected: " + text.getDetectedText() + " , Confidence: " + text.getConfidence().toString());
// //      			        		 System.out.println();
// //      			        		  }			              
// //      			         }
// //      			     } catch(AmazonRekognitionException e) {
// //      			    	  System.out.print("oops in the catch");
// //      			          e.printStackTrace();
// //      			      }
// //      		   }
// //             }
            
// //         } catch (JMSException e) {
// //            System.out.println("Please run the instance 1 first..");
// //         }
// //     }
// // }