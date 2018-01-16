import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Random;

import com.google.gson.GsonBuilder;
import com.google.gson.*;

public class NoobChain {
	
	public static ArrayList<Block> blockchain = new ArrayList<Block>();
	public static int difficulty = 5;


	public static void main(String[] args) {	
//		add our blocks to the blockchain ArrayList:
		System.out.println(blockchain.size());
		System.out.println("Trying to Mine block 1... ");
		addBlock(new Block("Hi im the first block", "0"));
		System.out.println(blockchain.size());


		System.out.println("Trying to Mine block 2... ");
		addBlock(new Block("Yo im the second block",blockchain.get(blockchain.size()-1).hash));

		System.out.println("Trying to Mine block 3... ");
		addBlock(new Block("Hey im the third block",blockchain.get(blockchain.size()-1).hash));

		System.out.println("Trying to Mine block 4... ");
		addBlock(new Block("Hey im the fouth block transactiin",blockchain.get(blockchain.size()-1).hash));


		for (int i=0;i<30;i++){
			System.out.println("trying to block" +i);
			System.out.println(blockchain.size());

			byte[] array = new byte[18]; // length is bounded by 7
			new Random().nextBytes(array);
			String generatedString = new String(array, Charset.forName("ASCII"));
			addBlock(new Block("data is  : " +generatedString   ,blockchain.get(blockchain.size()-1).hash));

		}



		System.out.println("\nBlockchain is Valid: " + isChainValid());
		
		String blockchainJson = StringUtil.getJson(blockchain);
		System.out.println("\nThe block chain: ");
		System.out.println(blockchainJson);
	}
	
	public static Boolean isChainValid() {
		Block currentBlock; 
		Block previousBlock;
		String hashTarget = new String(new char[difficulty]).replace('\0', '0');
		
		//loop through blockchain to check hashes:
		for(int i=1; i < blockchain.size(); i++) {
			currentBlock = blockchain.get(i);
			previousBlock = blockchain.get(i-1);
			//compare registered hash and calculated hash:
			if(!currentBlock.hash.equals(currentBlock.calculateHash()) ){
				System.out.println("Current Hashes not equal");			
				return false;
			}
			//compare previous hash and registered previous hash
			if(!previousBlock.hash.equals(currentBlock.previousHash) ) {
				System.out.println("Previous Hashes not equal");
				return false;
			}
			//check if hash is solved
			if(!currentBlock.hash.substring( 0, difficulty).equals(hashTarget)) {
				System.out.println("This block hasn't been mined");
				return false;
			}
			
		}
		return true;
	}
	
	public static void addBlock(Block newBlock) {
		newBlock.mineBlock(difficulty);
		blockchain.add(newBlock);
	}


}
