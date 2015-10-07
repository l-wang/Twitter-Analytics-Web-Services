# Twitter-Analytics-Web-Services
CMU 15-619 Cloud Computing Final Project

Frontend code:
   - Q1.java 
   - Q2HBase.java 
   - Q2MySql.java 
   - Q3HBase.java
   - Q3mysql.java
   - Q4Mysql.java
   - Q4HBase.java
   - Q5Mysql.java
   - Q6Mysql.java
are front end codes

ETL code:
   - Q2: EtlMapper.java, EtlReducer3.java, Words.java
      - Use EtlMapper.java and EtlReducer3.java for streaming mapreduce on EMR. 
      - EtlMapper.java parses time, userid, tweetId, score, and censored text and output them. 
      - EtlReducer.java reads the output from EtlMapper, sorts the result(tweetId:score:censored text) by tweetId and outputs the results. 
      - Words.java is a class called by EtlMapper.java. It helps to censor the words and calculate the sentimental scores.

   - Q3: Q3Mapper1.java, Q3Reducer1.java, Q3Mapper2.java, Q3Reducer2.java
      - Use (Q3Mapper1.java and Q3Reducer1.java) and (Q3Mapper2.java and Q3Reducer2.java) for two streaming mapreduces on EMR. (Q3Mapper1.java and Q3Reducer1.java) is used for generating intermediate data, and then use (Q3Mapper2.java and Q3Reducer2.java) to read the output files from the first mapreduce and then generate data to be loaded into databases. 

   - Q4: Q4Mapper1.java, Q4Reducer1.java, Q4Mapper2.java, Q4Reducer2.java, Cmptor.java
   - Use (Q4Mapper1.java and Q4Reducer1.java) and (Q4Mapper2.java and Q4Reducer2.java) for two streaming mapreduces on EMR. (Q4Mapper1.java and Q4Reducer1.java) is used for generating intermediate data. (Q4Mapper2.java and Q4Reducer2.java) reads the output files from the first mapreduce and then generate data to be loaded into databases.
Cmptor.java is a class called by Q4Reducer2.java. It helps to compare the rank of two hashtags.

   - Q5: Q5Mapper.java, Q5Reducer.java
      - Use Q5Mapper.java and Q5Reducer.java for streaming mapreduce on EMR. 

   - Q6: Q6Mapper.java, Q6Reducer.java, Q6Mapper2.java, Q6Reducer2.java
      - Use Q6Mapper.java and Q6Reducer.java for streaming mapreduce on EMR. 
      - Download the output file of Q6Reducer.java to local computer. 
      - Merge the output file into one file using the command: cat part* > data.txt
      - Run MapReduce locally using the command: 
         - cat data.txt | java -classpath Q6Mapper2.jar Q6Mapper2 | sort | java -classpath Q6Reducer2.jar Q6Reducer2
         - The final output file will be written. 

HBase code:
   - Content.java and NewCombinedHBase.java are for Q2 HBase.
   - RetweetHBase.java is for Q3 HBase.
   - HashtagHBase.java is for Q4 HBase.

