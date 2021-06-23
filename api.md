
# 상품 조회 
```
GET /products/{id}
```
Request Params

Name | Type | Description
---- | ---- | ---- 
id | int | 상품 id로 상품을 조회 합니다. | 뚝배기깹니다

Response
```
{
  "id" : 1,
  "subject" : "상품"
}
```

# 상품 검색 
```
GET /products?query={search_text}
```
Request Params

Name | Type | Description
---- | ---- | ---- 
query | string | text로 상품을 검색합니다. | 

Response
```
[
{
  "id" : 1,
  "subject" : "상품1"
},
{
  "id" : 2,
  "subject" : "상품2"
}
]

```
...