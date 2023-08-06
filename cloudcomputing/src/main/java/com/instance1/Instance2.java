package com.instance1;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;

import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.CreateQueueRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.amazonaws.services.sqs.model.ReceiveMessageRequest;

public class Instance2 {
    
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
