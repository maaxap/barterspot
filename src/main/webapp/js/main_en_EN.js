$(document).ready(function() {

    $("#registration-submit").click(function() {
        var form = $("#registration-form");
        var formObject = form.serializeArray();

        try {
            var user = {
                email:              formObject[1]['value'],
                password:           formObject[2]['value'],
                passwordConfirm:    formObject[3]['value'],
                name:               formObject[4]['value'],
                surname:            formObject[5]['value'],
                phoneNumber:        formObject[6]['value'],
                birthDay:           formObject[7]['value'],
                birthMonth:         formObject[8]['value'],
                birthYear:          formObject[9]['value'],
                address:            formObject[10]['value'],
                postIndex:          formObject[11]['value']
            };
        } catch(err) {
            console.log(err);
        }

        if(!checkEmail(user.email)) {
            showWarn("Please, enter your email in compatible format.");
        } else if(!checkPsw(user.password, user.passwordConfirm)) {
            showWarn("Passwords do not match or password fields are empty.");
        } else if(!checkUserNameSurname(user.name, user.surname)) {
            showWarn("Please, write your real name and surname. There should not be any punctuation.");
        } else if(!checkPhoneNumber(user.phoneNumber)) {
            showWarn("Please, enter compatible phone number.");
        } else if(!checkDateFormat(user.birthDay, user.birthMonth, user.birthYear)) {
            showWarn("Please, enter valid age.");
        } else if(!isBirthDate(user.birthDay, user.birthMonth, user.birthYear)) {
            showWarn("Sorry, you must be at least 18 years old.");
        } else if(!checkAddress(user.address)) {
            showWarn("Please, enter compatible address.");
        } else if(!checkPostIndex(user.postIndex)) {
            showWarn("Please, enter compatible post index.");
        } else {
            form.submit();
        }
    });

    $('#add-lot-submit').click(function() {
        var form = $('#add-lot-form');
        var formObject = form.serializeArray();

        try {
            var lot = {
                name:           formObject[1]['value'],
                description:    formObject[2]['value'],
                defaultPrice:   formObject[3]['value'],
                category:       formObject[4]['value'],
                day:            formObject[5]['value'],
                month:          formObject[6]['value'],
                year:           formObject[7]['value']
            };
        } catch(err) {
            console.log(err);
        }



        if(!checkLotName(lot.name)) {
            showWarn("Please, enter lot name in compatible format.");
        } else if(!checkDefaultPrice(lot.defaultPrice)) {
            showWarn("Please, enter default price in compatible format: not null positive value.");
        } else if(!checkDateFormat(lot.day, lot.month, lot.year) || !isFinishing(lot.day, lot.month, lot.year)) {
            showWarn("Please, enter valid finishing date.");
        } else {
            form.submit();
        }
    });

    $("#edit-profile-submit").click(function () {
        var form = $("#edit-profile-form");
        var formObject = form.serializeArray();

        try {
            var user = {
                name:          formObject[1]['value'],
                surname:       formObject[2]['value'],
                birthDay:      formObject[3]['value'],
                birthMonth:    formObject[4]['value'],
                birthYear:     formObject[5]['value'],
                phoneNumber:   formObject[6]['value'],
                address:       formObject[7]['value'],
                postIndex:     formObject[8]['value']
            };
        } catch(err) {
            console.log(err);
        }

        if(!checkUserNameSurname(user.name, user.surname)) {
            showWarn("Please, write your real name and surname. There should not be any punctuation.");
        } else if(!checkPhoneNumber(user.phoneNumber)) {
            showWarn("Please, enter compatible phone number.");
        } else if(!checkDateFormat(user.birthDay, user.birthMonth, user.birthYear)) {
            showWarn("Please, enter valid age.");
        } else if(!isBirthDate(user.birthDay, user.birthMonth, user.birthYear)) {
            showWarn("Sorry, you must be at least 18 years old.");
        } else if(!checkAddress(user.address)) {
            showWarn("Please, enter compatible address and post index.");
        } else if(!checkPostIndex(user.postIndex)) {
            showWarn("Please, enter compatible address and post index.");
        } else {
            form.submit();
        }
    });

    $("#settings-submit").click(function() {
        var form = $("#settings-form");
        var formObject = form.serializeArray();

        try {
            var user = {
                email:              formObject[1]['value'],
                newPsw:             formObject[2]['value'],
                password:           formObject[3]['value'],
                passwordConfirm:    formObject[4]['value'],
                locale:             formObject[5]['value']
            };
        } catch(err) {
            console.log(err);
        }

        if(!checkEmail(user.email)) {
            showWarn("Please, enter your email in compatible format.");
            return false;
        } else if(user.newPsw != null && user.newPsw.length != 0 && user.password != null  && !checkPsw(user.password, user.passwordConfirm)) {
            showWarn("Passwords do not match.");
        } else {
            form.submit();
        }
    });

    $("#add-bid-submit").click(function() {
        var form = $("#add-bid-form");
        var formObject = form.serializeArray();
        try {
            var bids = {
                lastBid:    formObject[2]['value'],
                bid:        formObject[3]['value']
            }
        } catch(err) {
            console.log(err);
        }

        /* Validate */
        if(!checkBidFormat(bids.bid) && !checkBidFormat(bids.lastBid)) {
            showWarn("Please, enter valid bid.");
        } else if(!checkBidValue(bids.bid, bids.lastBid)) {
            showWarn("New bid should be larger than previous at least on 1$.");
        } else {
            form.submit();
        }
    });

    $('#edit-lot-submit').click(function() {
        var form = $('#edit-lot-form');
        var formObject = form.serializeArray();

        if(formObject.length == 7) {
            return;
        }

        try {
            var lot = {
                name:           formObject[2]['value'],
                description:    formObject[3]['value'],
                category:       formObject[4]['value'],
                day:            formObject[5]['value'],
                month:          formObject[6]['value'],
                year:           formObject[7]['value']
            };
        } catch(err) {
            console.log(err);
        }

        if(!checkLotName(lot.name)) {
            showWarn("Please, enter lot name in compatible format.");
        } else if(!checkDateFormat(lot.day, lot.month, lot.year) || !isFinishing(lot.day, lot.month, lot.year)) {
            showWarn("Please, enter valid finishing date.");
        } else {
            form.submit();
        }
    });

    $('#add-category-submit').click(function () {
        $('#add-category-form').submit();
    });

    $('#send-email-submit').click(function () {
        var form = $('#send-email-form');
        var formObject = form.serializeArray();

        try {
            var email = {
                senderEmail:    formObject[1]['value'],
                senderPassword: formObject[2]['value'],
                receiverEmail:  formObject[3]['value'],
                subject:        formObject[4]['value'],
                text:           formObject[5]['value']
            }
        } catch(err) {
            console.log(err);
        }

        if(!checkEmail(email.senderEmail)) {
            showWarn("Sender email is not valid.");
        } else if(email.senderPassword.length < 1) {
            showWarn("Sender password field is empty.");
        } else if(!checkEmail(email.receiverEmail)) {
            showWarn("Receiver email is not valid.");
        }  else if(email.subject.length < 1) {
            showWarn("Subject field is empty.");
        } else if(email.text.length < 1) {
            showWarn("Text field is empty.");
        } else {
            form.submit();
        }
    });

    function checkEmail(email) {
        var pattern = new RegExp(/^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/);
        return email != null && email != "" && email.length < 320 && pattern.test(email);

    }

    function checkPsw(password, passwordConfirm) {
        return password != "" && password == passwordConfirm;
    }

    function checkUserNameSurname(name, surname) {
        var pattern = new RegExp(/^[A-Za-zА-Яа-я-&\s]{0,250}$/);
        return  name != null &&
            name != "" &&
            name.length < 251 &&
            surname != null &&
            surname != "" &&
            surname.length < 251 &&
            pattern.test(name) &&
            pattern.test(surname);
    }

    function checkDateFormat(day, month, year) {
        day =   toIntNumber(day);
        month = toIntNumber(month);
        year =  toIntNumber(year);
        return  (0 < day && day < 32) &&
            (0 < month && month < 13) &&
            (1900 < year && year< 2100);
    }

    function isBirthDate(day, month, year) {
        var now = new Date();
        var checkingDate = new Date(Number(year) + 18, month, day);
        return now - checkingDate >= 0;
    }

    function isFinishing(day, month, year) {
        return new Date() < new Date(year, month, day) ;
    }

    function checkPhoneNumber(phoneNumber) {
        var pattern = new RegExp(/^[0-9+]{1,15}$/);
        return  phoneNumber != null &&
            phoneNumber.length > 1 &&
            phoneNumber.length < 16 &&
            pattern.test(phoneNumber);
    }

    function checkAddress(address) {
        var pattern = new RegExp(/^[0-9\w\s.,&-\\+:()/#_]{1,250}$/);
        return  address != null &&
            address.length < 251 &&
            address.length > 0 &&
            pattern.test(address);
    }

    function checkPostIndex(postIndex) {
        var pattern = new RegExp(/^[0-9\w]{1,25}$/);
        return  postIndex != null &&
            postIndex.length < 25 &&
            postIndex.length > 0 &&
            pattern.test(postIndex);
    }

    function checkLotName(name) {
        return name != null && name != "" && name.length < 251;
    }

    function checkDefaultPrice(defaultPrice) {
        return toIntNumber(defaultPrice) >= 0;
    }

    function checkBidFormat(bid) {
        var pattern = new RegExp(/^[0-9]+\.?[0-9]*$/i);
        return pattern.test(bid);
    }

    function checkBidValue(bid, lastBid) {
        return toFloatNumber(bid) > toFloatNumber(lastBid);
    }

    function toIntNumber(num) {
        if(num == null) {
            return null;
        }
        return Math.round(Math.abs(Number(num)));
    }

    function toFloatNumber(num) {
        if(num == null) {
            return null;
        }
        return Math.abs(Number(num));
    }

    function showWarn(message) {
        $.notify({
            message: message
        }, {
            element: 'body',
            type: 'danger',
            newest_on_top: true,
            placement: {
                from: 'top',
                align: 'center'
            },
            delay: 3000,
            spacing: 5,
            offset: 66
        });
    }
});