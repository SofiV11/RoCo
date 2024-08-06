// Получить все кнопки "Редактировать"
   const editButtons = document.querySelectorAll('.edit-icon');
   const modal = document.getElementById('exampleModal');


   // Обработчик клика для каждой кнопки
   editButtons.forEach(button => {
       button.addEventListener('click', function() {
           // Получить ID пользователя из атрибута data-user-id
           const userId = this.dataset.userId;

           // Отправить AJAX-запрос на сервер для получения данных пользователя
           fetch("/api/users/" + userId)
               .then(response => response.json())
               .then(user => {
                   // Заполнить модальное окно данными пользователя

/*                   const contentDiv = document.createElement('div');
                   contentDiv.innerHTML = '<div th:insert=' + '"~{pattern :: profileForm}"' + '></div>';
                   modal.querySelector('.modal-content').appendChild(contentDiv);*/
//                   так не раьотает

                    fetch('/renderProfileForm', {
                        method: 'POST',
                        headers: {
                       'Content-Type': 'application/json',
                       'Accept': 'text/html; charset=utf-8'
                        },
                        body: JSON.stringify(user)
                    })
                    .then(response => response.text())
                    .then(html => {
                   // Вставьте рендеренный HTML в модальное окно


                        modal.querySelector('.modal-content').innerHTML = html;
                        modal.style.display = 'block';
                        const modalCloseButton = modal.querySelector('.btn-close');
                        modalCloseButton.addEventListener('click', () => {
                                  // Закрыть модальное окно
                                  modal.style.display = 'none';
                              });
                        // Добавьте обработчик клика по фону модального окна (для закрытия при клике вне окна)
//                        modal.addEventListener('click', function(event) {
//                                  if (event.target === this) {
//                                      modal.style.display = 'none';
//                                  }
//                              });
                        document.body.addEventListener('click', function(event) {
                          // Проверяем, кликнули ли по области вне модального окна
                          if (event.target !== modal && !modal.contains(event.target)) {
                            modal.style.display = 'none';
                          }
                        });

                        let isDragging = false;
                        let offsetX, offsetY;
                        modalHeader = modal.querySelector('.modal-dialog');

                        modalHeader.addEventListener('mousedown', (event) => {
                              isDragging = true;
                              offsetX = event.clientX - modal.offsetLeft;
                              offsetY = event.clientY - modal.offsetTop;
                            });

                      document.addEventListener('mousemove', (event) => {
                        if (isDragging) {
                          modal.style.left = event.clientX - offsetX + 'px';
                          modal.style.top = event.clientY - offsetY + 'px';
                        }
                      });

                      document.addEventListener('mouseup', () => {
                        isDragging = false;
                      });

//                      let imgId = user.userDetails.image.imageId;
//                      if(imgId != null){
//                          document.getElementById('preview').src = '/profileImages/' + imgId;
//                      } else modal.getElementById('preview').src = 'https://via.placeholder.com/150';

                    })
                    .catch(error => {
                        console.error('Ошибка при рендеринге шаблона:', error); });




                   // ... заполнить другие поля модального окна
                   //modal.style.display = 'block';
               })
               .catch(error => {
                   console.error('Ошибка при получении данных пользователя:', error);
               });
       });
   });



function previewImage(file) {
  const reader = new FileReader();
  reader.onload = () => document.getElementById('preview').src = reader.result;
  reader.readAsDataURL(file);
  document.querySelector('.close-icon').style.display = 'block';
  document.querySelector('.close-icon').classList.add('show');
}

function removeImage() {
document.getElementById('preview').src = "https://via.placeholder.com/150";
document.querySelector('.close-icon').style.display = 'none';
document.querySelector('.close-icon').classList.remove('show');
}


/*
const menuItems = document.querySelectorAll('.menu_it');
const content = document.getElementById('my-acc-content');

// Функция для обновления URL и контента
function updateContent(url, menuItem) {
    window.location.href = url;

    // Удаляем класс "selected" у всех элементов меню
    menuItems.forEach(item => item.classList.remove('selected'));
    // Добавляем класс "selected" к выбранному элементу
    menuItem.classList.add('selected');
}

// Обработка кликов на элементы меню
menuItems.forEach(menuItem => {
    menuItem.addEventListener('click', (event) => {
        event.preventDefault(); // Предотвращаем стандартный переход по ссылке
        const link = menuItem.querySelector('a');
        const url = link.getAttribute('href'); // Получаем URL из атрибута "href"
        updateContent(url, menuItem);
    });
});

//  Загружаем начальный контент по умолчанию
const defaultUrl = window.location.href;
updateContent(defaultUrl, document.getElementById('my-acc-content'));*/
