Its a java wrapper around Canberra Genie 2000 library (made for and tested on versions 3.2 and 3.3). 

Author: Jacek Bzdak jbzdak@gmail.com
Description: It was made for my BA on Faculty of Physics of Warsaw University of Technology

It is fairly undocumented :(, and most of Javadocs are in Polish. So using it may be a bit hard. 
If you need any help please send me an email, and I will try to help you. 

Good places to start using or developing are: SimpleConnector, GenieConnector, LibraryConnector and GenieLibrary classes. 

SimpleConnector and GenieConnector wrap DSC pointers (DSC is a Genie 2000 concept meaning Data Source Context). GenieLibrary is an JNA (Java Native Access) binding class. LibraryConnectir is some intermediate layer between these two. 

Well to make it work you need to:

1. Prepare Canberra Genie Libraries for binding with JAVA (I can't distribute them due to license restricitons), 
2. Prepare this program to use these libraries. 
3. Prepare enviorment. 


1. If you got dynamic libraries you're set up :) just use those dlls. 

If not (this was my case) you will need to prepare dynamic libs for linking with JNA (that is used internally). 

On linux GCC is able to convert them automatically, but since I used windows I don't know the magic. 

On windows it is more complicated. 
First you need to setup Visual Studio (there are free Express editions), or any other compiler. Create dll library project. Link to Genie Library dev files (that you have!) and prepare dll that wraps and exports every SAD call: 

__declspec(dllexport) SADENTRY DLL_WRAPPER_SadLoadedMidFiles(HMEM param0, 
            USHORT usMaxMidFiles, VOID* param2){
      return SadLoadedMidFiles(param0, usMaxMidFiles, param2);
  }

You have to prefix every function call so names won't clash. 

You can do this automatically using my wrapper generator see: http://stackoverflow.com/questions/845183/convert-static-windows-library-to-dll. Wrapper generator is also aviable at my maven repo: http://skimbleshanks.ath.cx/maven2, with coordinates dies-irae:wrapper-generator:1.0. 

After that you need to compile dll and youre good to go.

2. There is one thing that you need to do for this program to use your dynamic lib -- you need to modify genieConnector.properties in src/main/resources. It has two properties first (genieConnector.dllName) is name (optionally with path) to dll that you will link it to (you have created that dll in step 1). Second is prefix that is appended to every call. 

3. Finally you need to copy dll to path you specified for genieConnector.dllName property. If you didn't specify it with a path copy it somewhere to java.library.path. 
