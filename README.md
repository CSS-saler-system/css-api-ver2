
"@css-api(https://happy-bee-api.azurewebsites.net)"
<!-- ![GitHub branch checks state](https://img.shields.io/github/checks-status/css-saler-system/css-api-ver2/develop?color=green) -->
"@figma(https://www.figma.com/file/IbtI5Kdh8rf5YnJPvhIJiU/CSS?node-id=0%3A1)"


"@Processing(https://docs.google.com/spreadsheets/d/1rDqd08oqSDx8zCZpwptwkM08bClxDIj8mrbsULxApjE/edit#gid=692238097)"

Bug parse LocalDate to Json: 
@JsonSerialize(using = LocalDateSerializer.class)
@JsonDeserialize(using = LocalDateDeserializer.class) 
@JsonFormat(pattern = "yyyy/MM/dd")

@JsonSerialize(using = LocalDateTimeSerializer.class)
@JsonDeserialize(using = LocalDateTimeDeserializer.class)
@JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")

<h4>AccountCreator - Json</h4>
{
"name" : "",<br>
"phone" : "",<br>
"email" : "",<br>
"password" : "",<br>
"address" : "",<br>
"description" : "",<br>
"gender" : true,<br>
"role" : "",<br>
"dayOfBirth" : "2022/05/26"
}
<h4>AccountUpdater - Json</h4>
{
"id" : null,
"name" : "",
"dob" : null,
"phone" : "",
"email" : "",
"address" : "",
"description" : "",
"gender" : null,
"identityImage" : null,
"avatar" : null,
"license" : null
}

<h4>Product Creator</h4>
{
"creatorAccountId" : "55b90b8d-6977-423d-a587-c720dc4b347d",
"categoryId" : "4a47954b-99ba-dd4b-8c6d-78ad33ed6420",
"name" : "Samsung Furniture",
"brand" : "Samsung",
"shortDescription" : "This is future furniture for every one",
"description" : "This is long term support for every one like clean up house",
"quantity" : 123,
"price" : 13.0,
"pointSale" : 13.0
}

=================================================================
{
"creatorAccountId" : "81aec658-ce38-2e4d-be22-06e05ea06259",
"categoryId" : "687c91a0-0012-c04d-8e73-17f616d4f8ec",
"name" : "TV LCD",
"brand" : "LG",
"shortDescription" : "The best tv for cheap price",
"description" : "the best led tv, ",
"quantity" : 13.3,
"price" : 13.0,
"pointSale" : 13.0
}

{
"name" : "Josh Long",
"phone" : "123498",
"email" : "Long@gmail.com",
"password" : "123",
"address" : "Texas",
"description" : "This person good at java",
"gender" : true,
"role" : "Enterprise",
"dayOfBirth" : "2022/05/26"
}

