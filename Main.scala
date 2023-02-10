//> using lib "com.github.seratch::awscala-s3:0.9.2"


import awscala.s3.*
import awscala.*
import scala.concurrent.*
import duration.Duration.Inf

given region : Region = Region.EU_NORTH_1
//S3(accessKeyId: String, secretAccessKey: String)
//val s3 = S3(aws_access_key_id, aws_secret_access_key)

//val objects = s3.listObjectsV2("peformancetest")
//objects.getObjectSummaries

def timeit( f : () => Unit ) : Long =
   val before =  System.currentTimeMillis
   f()
   val after = System.currentTimeMillis
   after - before

import java.io.*
val r  = util.Random
val bigArray = r.nextBytes(1024*1024*256)

def distribution() : Int =
   val i = r.nextInt(100)
   if(i < 80)
      r.nextInt(1256)+256
   else if (i < 95)
      r.nextInt(1024*100)+1512
   else
      r.nextInt(1024*1024*100) + 1

case class DataGen(offset: Int, size: Int)

def doStuff(dg: DataGen) : Unit = 
   val s =  new ByteArrayInputStream(bigArray, dg.offset, dg.size)
   s.readAllBytes()
   s.close()

import java.util.concurrent.Executors
given ExecutionContext = ExecutionContext.fromExecutor(Executors.newFixedThreadPool(2))

val result = for(i <- 1.to(100)) {
val idx = for ( i <- 1.to(100)) yield DataGen(r.nextInt(1024*1024*127), distribution())
val time = timeit(
   () =>
   idx.map( dg => Future{ 
   doStuff(dg)
   } ).foreach(Await.result(_, Inf))
   
)
val s = idx.map(dg => dg.size).sum
(s,time)
} 
/*idx.map( dg => Future{ 
   doStuff(dg)
   } ).map(Await.result(_, Inf))
   println(idx.map(dg => dg.size).sum)
*/

//timeit(() => stream.readAllBytes())