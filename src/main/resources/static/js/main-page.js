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

function doTheMagic(){
let btnNext = document.querySelector('.next');
let btnPrevious = document.querySelector('.previous');
let overflow = document.querySelector('.overflow');
let block = document.querySelector('.bloco');
let allBlocks = document.querySelectorAll('.bloco');
let blockWidth = block.offsetWidth;

let position = 0;
let maxWidth = overflow.offsetWidth;
let allBlocksWidth = allBlocks.length*blockWidth;

  if(allBlocksWidth < maxWidth){
    btnPrevious.style.opacity = "0";
    btnNext.style.opacity = "0";
  }

function togglePrev(position){
  if(position >= blockWidth){
    btnPrevious.style.opacity = "1";
  } else {
    btnPrevious.style.opacity = "0";
  }
}

function toggleNext(position){
  if((allBlocksWidth-position) > maxWidth){
    btnNext.style.opacity = "1";
  } else {
    btnNext.style.opacity = "0";
  }
}

btnNext.onclick = function(){
  if((allBlocksWidth-position) > maxWidth){
    position = position+blockWidth;
    overflow.style.transform = `translateX(-${position}px)`;
  }
  togglePrev(position);
    toggleNext(position);
}

btnPrevious.onclick = function(){
  if(position >= blockWidth){
    position = position-blockWidth;
    overflow.style.transform = `translateX(-${position}px)`;
  }
    togglePrev(position);
  toggleNext(position);
}

}



function resize() {
  if(window.innerWidth < 768){
    let overflow = document.querySelector('.overflow');
    overflow.style.transform = `translateX(0px)`;
    doTheMagic();
  } else {
    doTheMagic();
  }
}

window.onresize = resize;
doTheMagic();