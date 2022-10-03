function productList() {
  // Call Web API to get a list of Product
  $.ajax({
    url: '/api/Product/',
    type: 'GET',
    dataType: 'json',
    success: function (products) {
      productListSuccess(products);
    },
    error: function (request, message, error) {
      handleException(request, message, error);
    }
  });
}