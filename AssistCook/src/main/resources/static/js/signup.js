/**
 * signup.js
 * 회원 가입 시 사용되는 스크립트
 */

function checkPassword() {
    let password = document.getElementById('pw').value;
    let confirmPassword = document.getElementById('pwCheck').value;
    let checkPasswordMsg = document.getElementById('checkPassword');

    if (password == confirmPassword) {
        checkPasswordMsg.innerText = '비밀번호가 일치합니다.';
        checkPasswordMsg.classList.remove('error-message');
        checkPasswordMsg.classList.add('success-message');
    } else {
        checkPasswordMsg.innerText = '비밀번호가 일치하지 않습니다.';
        checkPasswordMsg.classList.remove('success-message');
        checkPasswordMsg.classList.add('error-message');
    }
}

function daumPostcode() {
    new daum.Postcode({
        oncomplete: function(data) {
            var addr = ''; // 주소 변수
            var extraAddr = ''; // 참고항목 변수

            if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                addr = data.roadAddress;
            } else { // 사용자가 지번 주소를 선택했을 경우(J)
                addr = data.jibunAddress;
            }

            // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
            if(data.userSelectedType === 'R'){
                // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                    extraAddr += data.bname;
                }
                // 건물명이 있고, 공동주택일 경우 추가한다.
                if(data.buildingName !== '' && data.apartment === 'Y'){
                    extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
                // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                if(extraAddr !== ''){
                    extraAddr = ' (' + extraAddr + ')';
                }
                // 조합된 참고항목을 해당 필드에 넣는다.
                document.getElementById("extraAddress").value = extraAddr;

            } else {
                document.getElementById("extraAddress").value = '';
            }

            // 우편번호와 주소 정보를 해당 필드에 넣는다.
            document.getElementById('postcode').value = data.zonecode;
            document.getElementById("address").value = addr;
            // 커서를 상세주소 필드로 이동한다.
            document.getElementById("detailAddress").focus();
        }
    }).open();
}

function selectDomain() {
    document.getElementById("domain").value = document.getElementById("domainList").value;
}

function isDuplication() {
    let checkId = document.getElementById("login_id").value;
    fetch(`/api/idDuplication?loginId=${encodeURIComponent(checkId)}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            let result = document.getElementById("idDuplicateResult");
            result.innerText = data.message;
            if (data.message == "사용 가능한 아이디 입니다.") {
                result.classList.remove("error-message");
                result.classList.add("success-message");
            } else {
                result.classList.remove("success-message");
                result.classList.add("error-message");
            }
        })
        .catch(error => {
        });
}

function requestEmailVerificationCode() {
    console.log("인증 코드 전송 요청");
    let email = document.getElementById('email').value + "@" + document.getElementById('domain').value;
    console.log("전송할 이메일: " + email);
    fetch('/api/requestEmailVerificationCode',{
        method: 'POST',
        headers: {
            'Content-Type' : 'application/json'
        },
        body: JSON.stringify(email)
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            console.log('Response Data:' + data.message);
        })
        .catch(error => {
            console.log('Error');
        })
}

function isCorrectCode() {
    console.log("인증 코드 확인");
    let inputCode = document.getElementById('checkCode').value;
    let email = document.getElementById('email').value + "@" + document.getElementById('domain').value;
    console.log("입력 인증 코드");
    fetch(`/api/checkVerificationCode?checkCode=${inputCode}&requestEmail=${encodeURIComponent(email)}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then(response => {
            return response.json();
        })
        .then(data => {
            let result = document.getElementById('checkCodeResult');
            result.innerText = data.message;
            if (data.message=='인증 완료') {
                result.classList.remove('error-message');
                result.classList.add('success-message');
            } else {
                result.classList.remove('success-message');
                result.classList.add('error-message')
            }
        })
        .catch(error => {
        });
}