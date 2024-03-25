// const leanMoreBtn =  document.getElementById('leanmore')
// leanMoreBtn.onclick = function(){
//     console.log('clicked')
// }


// window.addEventListener("DOMContentLoaded", event => {
//     console.log("started")
//     const leanMoreBtn =  document.getElementById('leanmore')
  
//     const handleClick = () => {
//       //console.log("How are you?")
//     }
//     // document.getElementById('hider').onclick = function() {
//     //     document.getElementById('text').hidden = true;
//     //   }
  
//     leanMoreBtn.addEventListener("click", handleClick)
//   })

var sliderContent = document.querySelector("div.slider__line")
var sliderElements = document.querySelectorAll("div.row")
var arrowToPrev = document.querySelector('button.arrowToPrev')
var arrowToNext = document.querySelector('button.arrowToNext')

var blockW = document.querySelector("div.slider").offsetWidth;
var contentW = sliderContent.offsetWidth;
var dist = contentW - blockW;
// sliderBlock = document.querySelector('.slider')

let sliderCount = 2, 
elementWidth = sliderElements[0].offsetWidth,//document.querySelector('.row').offsetWidth,
position = 0;

arrowToPrev.onclick = function (){
    // sliderCount--;
    // if(sliderCount < 0 ) sliderCount = sliderElements.length - 4;


    //sliderContent.style.marginLeft = position + 'px';
    position += elementWidth * sliderCount;
    position = Math.min(position, 0)
    sliderContent.style.transform = 'translateX(' + position + 'px)';
    // rollSlide();

}
arrowToNext.onclick = function (){
    // sliderCount+=3;
    // if(sliderCount >= sliderElements.length/4) sliderCount = 0;

    // rollSlide();


    // sliderContent.style.marginLeft = position + 'px';
    position -= elementWidth * sliderCount;
    position = Math.max(position, -elementWidth * (sliderElements.length - sliderCount));
    // max = 1220 with SliderWidth =
    
    sliderContent.style.transform = 'translateX(' + position + 'px)';
};


// function showSlide(){
//     sliderWidth = document.querySelector('.slider__line').offsetWidth; 
// }
// showSlide();

// function nextSlide(){
//     sliderCount+=3;
//     if(sliderCount >= sliderElements.length/4) sliderCount = 0;

//     rollSlide();
//     // thisSlide(sliderCount);
// }

// function prevSlide(){
//     sliderCount--;
//     if(sliderCount < 0 ) sliderCount = sliderElements.length - 4;

//     rollSlide();
//     // thisSlide(sliderCount);
// }

// function rollSlide(){
//     //sliderContent.style.transform = 'translateX(${sliderCount * sliderWidth}px)'
//     sliderContent.style.transform = 'translateX(-70px)';
// }

// $('.slider-for').slick({
//     slidesToShow: 1,
//     slidesToScroll: 1,
//     arrows: false,
//     fade: true,
//     asNavFor: '.slider-nav'
//   });
//   $('.slider-nav').slick({
//     slidesToShow: 3,
//     slidesToScroll: 1,
//     asNavFor: '.slider-for',
//     dots: true,
//     focusOnSelect: true
//   });
 
//   $('a[data-slide]').click(function(e) {
//     e.preventDefault();
//     var slideno = $(this).data('slide');
//     $('.slider-nav').slick('slickGoTo', slideno - 1);
//   });