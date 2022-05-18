## The Gilded Rose Expands

The Gilded Rose* is a small inn in a prominent city that buys and sells only the finest items. The shopkeeper, Terry, is looking to expand by providing merchants in other cities with access to the shop's inventory via a public HTTP-accessible API. The Gilded Rose would like to utilize a surge pricing model like `Uber` to increase the price of each item by 10% if it has been viewed more than 10 times in an hour.

### API requirements
- Retrieve the current inventory (i.e. list of items)
- View an individual Item by name
- Buy an Item
  - authentication needed

**Here is the definition for an item:**

```java
class Item {
    public String name;
    public String description;
    public int price;
}
```

**A couple questions to consider:**
1. How do we know if a user is authenticated?
2. Is it always possible to buy an item?

Please model (and test!) accordingly. A simple H2 in-memory database is already configured.

### Deliverables

1. Complete this skeleton project with the required functionality to achieve the API requested
   1. use the in-memory DB
   3. all required classes in a consistent package structure
2. A system that can process the two API requests via HTTP
3. An authentication system (choose your system but prepare to talk about your choice)
4. Appropriate tests (unit, integration etc)
5. The code should be published in a public accessible repository - GitHub, gitlab, bitbucket, you choose, we just need be able to clone it :)

### Summary

Prepare a little summary of your application. Write [here](SUMMARY.md). We will chat over it on the next meeting.

1. Surge pricing functionality design
2. API endpoints design
3. Choice of data format. Include one example of a request and response.
4. What authentication mechanism was chosen, and why?
5. Testing structure

### Provided Resources

* A simple Maven-managed project configuration
* Pre-configured Spring Boot Application with the basics and an in-memory DB
* Use this JDK 11 binary if you don't have one
  * Linux: [tar.gz](https://github.com/adoptium/temurin11-binaries/releases/download/jdk-11.0.14.1%2B1/OpenJDK11U-jdk_x64_linux_hotspot_11.0.14.1_1.tar.gz)
  * MacOS: [pkg](https://github.com/adoptium/temurin11-binaries/releases/download/jdk-11.0.14.1%2B1/OpenJDK11U-jdk_x64_mac_hotspot_11.0.14.1_1.pkg) or [tar.gz](https://github.com/adoptium/temurin11-binaries/releases/download/jdk-11.0.14.1+1/OpenJDK11U-jdk_x64_mac_hotspot_11.0.14.1_1.tar.gz)
  * Windows: [msi](https://github.com/adoptium/temurin11-binaries/releases/download/jdk-11.0.14.1+1/OpenJDK11U-jdk_x64_windows_hotspot_11.0.14.1_1.msi) or [zip](https://github.com/adoptium/temurin11-binaries/releases/download/jdk-11.0.14.1+1/OpenJDK11U-jdk_x64_windows_hotspot_11.0.14.1_1.zip)

---
