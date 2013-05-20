import java.util.LinkedList;
import java.util.Random;

class TestConvex{
	static final String separator = "=====================";
	public static void main(String[] args){
		test1();
		test2();
		test3();
		if (args.length >= 1){
			test4();
			test5();
			test6();
		}
	}
	
	//einfaches Polygon aus 4 Ecken
	//alle Steigungen ungleich 0, alle Koordinaten groesser 0, keine kollinearen Kanten
	public static void test1(){
		printTest(1);
		
		LinkedList<Punkt> l = new LinkedList<Punkt>();
		l.add( new Punkt(1,1));
		l.add( new Punkt(9,2));
		l.add( new Punkt(7,7));
		l.add( new Punkt(3,4));
		
		Punkt[] p =  new Punkt[l.size()];
		for( int i=0; i< p.length; i++) p[i] = l.get(i);
		LinkedList<Punkt> result = ConvexHull.simpleConvex(p);
		printList(result);
	}
	
	public static void test2(){
		printTest(2);
		
		LinkedList<Punkt> l = new LinkedList<Punkt>();
		l.add( new Punkt(-1,1));
		l.add( new Punkt(-4,5));
		l.add( new Punkt(-6,2));
		
		Punkt[] p =  new Punkt[l.size()];
		for( int i=0; i< p.length; i++) p[i] = l.get(i);
		LinkedList<Punkt> result = ConvexHull.simpleConvex(p);
		printList(result);
	}
	
	public static void test3(){
		printTest(3);
		
		LinkedList<Punkt> l = new LinkedList<Punkt>();
		l.add( new Punkt(-8,1));
		l.add( new Punkt(-1,1));
		l.add( new Punkt( 0,7));
		l.add( new Punkt( 0,0));
		l.add( new Punkt( 0,-4));
		l.add( new Punkt( 1,-5));
		l.add( new Punkt( 2,3));
		l.add( new Punkt( 6,0));
		
		Punkt[] p =  new Punkt[l.size()];
		for( int i=0; i< p.length; i++) p[i] = l.get(i);
		LinkedList<Punkt> result = ConvexHull.simpleConvex(p);
		printList(result);
	}
	
	public static void test4(){
		printTest(4);
		Random gen = new Random();
		LinkedList<Punkt> l = new LinkedList<Punkt>();
		for( int i = 0; i<15; i++){
			l.add( new Punkt(gen.nextInt(160)-90,gen.nextInt(160)-90));
		}
		l.add( new Punkt( -100, -100));
		for( int i = 0; i<15; i++){
			l.add( new Punkt(gen.nextInt(160)-90,gen.nextInt(160)-90));
		}
		l.add( new Punkt(  100,  100));
		for( int i = 0; i<15; i++){
			l.add( new Punkt(gen.nextInt(160)-90,gen.nextInt(160)-90));
		}
		l.add( new Punkt(  100, -100));
		for( int i = 0; i<15; i++){
			l.add( new Punkt(gen.nextInt(160)-90,gen.nextInt(160)-90));
		}
		l.add( new Punkt(  -100,  100));
		
		Punkt[] p =  new Punkt[l.size()];
		for( int i=0; i< p.length; i++) p[i] = l.get(i);
		LinkedList<Punkt> result = ConvexHull.simpleConvex(p);
		printList(result);
	}
	
	public static void test5(){
		printTest(5);
		Random gen = new Random();
		LinkedList<Punkt> l = new LinkedList<Punkt>();
		
		for( int i = 0; i<50; i++){
			int x = gen.nextInt(999);
			int y = gen.nextInt(999);
			if( y >= x || x+y >=999) y = 998 - x;
			if(y < 0) y = 0;
			l.add( new Punkt(x,y));
		}
		{
			Punkt[] p =  new Punkt[l.size()];
			for( int i=0; i< p.length; i++) p[i] = l.get(i);
			LinkedList<Punkt> result = ConvexHull.simpleConvex(p);
			printList(result);
		}
		System.out.println("Sollte innerhalb dieses Dreiecks liegen:");
		
		l.add( new Punkt( 0, 0));
		l.add( new Punkt( 1000,  0));
		l.add( new Punkt( 0,  1000));
		
		Punkt[] p =  new Punkt[l.size()];
		for( int i=0; i< p.length; i++) p[i] = l.get(i);
		LinkedList<Punkt> result = ConvexHull.simpleConvex(p);
		printList(result);
	}
	
	public static void test6(){
		printTest(6);
		Random gen = new Random();
		LinkedList<Punkt> l = new LinkedList<Punkt>();
		
		for( int i = 0; i<400; i++){
			int x = gen.nextInt(999);
			int y = gen.nextInt(999);
			l.add( new Punkt(x,y));
		}
		
		Punkt[] p =  new Punkt[l.size()];
		for( int i=0; i< p.length; i++) p[i] = l.get(i);
		LinkedList<Punkt> result = ConvexHull.simpleConvex(p);
		printList(result);		
	}
	
	public static void printTest(int t){
		System.out.println();
		System.out.println(separator);
		System.out.println("Test "+t);
		System.out.println(separator);	
	}
	
	public static void printList(LinkedList<Punkt> l){
		for( Punkt p : l) { 
			System.out.print(p); 
			System.out.print(" ");
		}
		System.out.println();
	}
	
}
