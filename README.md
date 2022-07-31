
"@css-api(https://css-api.azurewebsites.net)"
<!-- ![GitHub branch checks state](https://img.shields.io/github/checks-status/css-saler-system/css-api-ver2/develop?color=green) -->
"@figma(https://www.figma.com/file/IbtI5Kdh8rf5YnJPvhIJiU/CSS?node-id=0%3A1)"

"@Enterprise(https://jolly-forest-0dfe3b200.1.azurestaticapps.net/)"

"@Processing(https://docs.google.com/spreadsheets/d/1rDqd08oqSDx8zCZpwptwkM08bClxDIj8mrbsULxApjE/edit#gid=692238097)"

Bug parse LocalDate to Json: 
@JsonSerialize(using = LocalDateSerializer.class)
@JsonDeserialize(using = LocalDateDeserializer.class) 
@JsonFormat(pattern = "yyyy-MM-dd")

@JsonSerialize(using = LocalDateTimeSerializer.class)
@JsonDeserialize(using = LocalDateTimeDeserializer.class)
@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")

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