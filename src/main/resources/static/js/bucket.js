var el;
var min = 0;
var step = 1;
let input = $(".quantity");
//var inputValue = Number(input.getAttribute("value") || 0);
let inputNumber = Number(input.children(".qty").val())
let totalPrice = input.parents("td").parents("tr").children(".total-price")

$("tr").each(function() { 
  var item = $(this).children(".item_cart");
  var txt = item.children(".produc-price").children("input").val();
  var subtotal = parseFloat(txt);
  var amountTxt = item.children(".product-quantity").children(".quantity").children("input").val();
  var amount = parseFloat(amountTxt);
  item.children(".total-price").text((Math.round(
                                            subtotal*amount*100
                                          )/100).toFixed(2));
});

// function increase(){
//   var current = parseFloat($(this).parent(".quantity").children(".qty").val())
//   current++
//   $(this).parent(".quantity").children(".qty").val(current);
  //inputNumber += step;
  //input.children(".qty").val(inputNumber);
  // var price = parseFloat(input.parents("td").parents("tr").children(".produc-price").text());
  // var amount = parseFloat(input.parents("td").parents("tr").children(".quantity").children("input").val());
  // totalPrice.text(
  //         (Math.round(
  //           price*amount*100
  //         )/100).toFixed(2) + "$");
  // changed();

//}
// function decrease(){
//   inputNumber -= step;
//   input.children(".qty").val(inputNumber);
  // var price = parseFloat(input.parents("td").parents("tr").children(".produc-price").text());
  // var amount = parseFloat(input.parents("td").parents("tr").children(".quantity").children("input").val());
  // totalPrice.text(
  //         (Math.round(
  //           price*amount*100
  //         )/100).toFixed(2) + "$");
  // changed();
//}

// $(".plus").click(function() {
//   $(this).parents("td").parents("tr").remove();
//   var newQty = parseInt($(this).parent("td").children(".qty").val()) + 1; 
//   $(this).parent("td").children(".qty").val(newQty) 
// });
// $(".minus").click(function() {
//   $(this).parents("td").parents("tr").remove();
//   var newQty = parseInt($(this).parent("td").children(".qty").val()) - 1; 
//   $(this).parent("td").children(".qty").val(newQty) 
// });

$(".quantity > input").bind("change keyup", function() {
  if (parseFloat($(this).val())<1) {
    $(this).val(1);
    el = $(this).parents("td").parents("tr").children(".remove");
    el.addClass("hey");
    setTimeout(function() {
      el.removeClass("hey");
    }, 200);
  }
  var price = parseFloat($(this).parents("td").parents("tr").children(".produc-price").children("input").val());
  //var amount = parseFloat($(this).parents("td").parents("tr").children(".quantity").children("input").val());
  var amount = parseFloat($(this).val());
  $(this).parents("td").parents("tr").children(".total-price").text(
                                          (Math.round(
                                            price*amount*100
                                          )/100).toFixed(2) + "$");
  changed();
});



$(".remove > img").click(function() {
  $(this).parents("td").parents("tr").remove();
  changed();
});

function changed() {
  var subtotal = 0;
  $(".item_cart").each(function() {
    subtotal += parseFloat($(this).children(".total-price").text());
  });
  $(".subtotal").text((Math.round(subtotal*100)/100).toFixed(2)+"$");
  //var a = (subtotal/100*105)+parseFloat($(".shipping").text())
  var shipping = subtotal/5
  $(".shipping").text(shipping);
  //var total = (Math.round(a*100)/100).toFixed(2);
  var total = Math.round(subtotal + shipping).toFixed(2) ;
  $(".total_sum").text(total);
  //$(".taxval").text("($"+(Math.round(subtotal*5)/100).toFixed(2)+") ");
}

// $("#checkout").click(function() {
//   alert("And that's $"+$(".realtotal").text()+", please.");
// });

// changed();

// $("#expand").click(function() {
//   $("#coolstuff").toggle();
// });