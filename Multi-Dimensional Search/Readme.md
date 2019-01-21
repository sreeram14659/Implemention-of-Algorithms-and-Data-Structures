Multi-dimensional search

Sreeram Chittela - sxc180025

IDE used Eclipse Photon Release (4.8.0).

- Create a new Java project.
- Paste the MDS code in a new java class.
- Compile MDSDriver.java.

Steps to compile and run code:
1. Run MDSDriver.java.
2. Select the operation from the following list 
	Insert
	Delete
	Find
	FindMinPrice
	FindMaxPrice
	FindPriceRange
	PriceHike
	RemoveNames
3. After entering all operations to be performed, type 'End' and check for output.

Implemented Multi-dimensional search with following Data Structures.
1.idandPriceMap(HashMap): Stores key=id and value=price
2.idandDescMap(HashMap): Stores key= one of the long ints in the description,
			 value = Arraylist of ids (If the description of ids 
			 contains the key)
3.tree1(TreeMap): Stores key=id and value = list of description

Implementation of operations:
1.Insert: Inserted into all the three data structres based on their fields.

2.Delete: Deleted an entry from all the three data strutures.

3.Find: if the id is present in idandPriceMap return the price else
return 0.

4.FindMinPrice(n):Get all the ids in which has long n using idandDescMap.

For those ids get prices from the idandPriceMap and return minimum value among them.
5.FindMaxPrice(n):Get all the ids in which has long n using idandDescMap.
For those ids get prices from the idandPriceMap and return maximum value among them.

6.FindPriceRange(n,l,h):Get all the ids in which has long n using idandDescMap.
For those ids get prices from the idandPriceMap and check if the price is in between l and h. If yes, increase the count.

7.PriceHike(l,h,r):Get all the ids in between l and h using treemap and get the price of those ids using idandPricemap, increase the price by rate r and update in the price in idandPricemap.

8.RemoveNames(id,list):Get the list of description for the particular id using tree map.If the tree map description list contains given items of list, delete it and update tree map and idanddescMap.
