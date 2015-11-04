/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

$(function(){
    
    $('#find').click(function(){
            var options = new ContactFindOptions();
                options.filter="";
                options.multiple=true; 
                var fields = ["*"];
        
                navigator.contacts.find(fields, function(contacts){
                    console.log("@@contacts"+contacts);
                    if(contacts != null && contacts.length > 0){
                        $("#contactshowul").html("");
                        for(var i=0; i<contacts.length; i++){
                            if(contacts[i]==null || contacts[i].displayName== null){
                                contacts.splice(i, 1);
                            }
                        }
                        
                        var cSort = function(a, b) {
                            var aName = a.displayName;
                            var bName = b.displayName;
                            return aName > bName ? 1 : (aName == bName ? 0 : -1);
                        };
                        
                        contacts.sort(cSort);
                        
                        for(var i=0; i<contacts.length; i++){
                            var number = "";
                            
                            if(contacts[i].phoneNumbers != null && contacts[i].phoneNumbers.length > 0){
                                for(var j=0;j<contacts[i].phoneNumbers.length;j++){
                                    if(contacts[i].phoneNumbers[j].type=='mobile'){
                                        number = contacts[i].phoneNumbers[j].value;
                                    }
                                }
                                
                            }
                            if(contacts[i].displayName!=null){
                                $("#contactshowul").append("<li><a href='index.html'>"+contacts[i].displayName +" "+  number+"</a></li>");
                            } 
                        }
                        $('#contactshowul').listview('refresh');
                    }else{
                    alert("No Contacts Available");    
                    }
                }, function(){
                    alert("Error getting Contacts");
                }, options);
    }
        );
    
});

    
function saveContact()
{
    var displayName = $('#dispName').val();
    var nickName = $('#nName').val();
    var givenName = $('#gName').val();
    var familyName = $('#fName').val();
    var workTel = $('#wTel').val();
    var mobileTel = $('#mTel').val();
    var homeTel = $('#hTel').val();
    
    var myContact = navigator.contacts.create({"displayName": displayName});
    myContact.nickname = nickName;
    myContact.name = givenName + " " + familyName;
    
    var phoneNumbers = [];
phoneNumbers[0] = new ContactField('work', workTel, false);
phoneNumbers[1] = new ContactField('mobile', mobileTel, true); // preferred number
phoneNumbers[2] = new ContactField('home', homeTel, false);
myContact.phoneNumbers = phoneNumbers;
    
    myContact.save(function(){
    navigator.notification.alert('Contact saved Succesfully', alertDismissed, 'Add Contact', 'Ok');
    }, function(msg){
    navigator.notification.alert('Error saving contact: '+msg);
    });
    console.log("The contact, " + myContact.displayName + ", note: " + myContact.note);
}

function alertDismissed(){
        //$.mobile.pageContainer.pagecontainer("change", "index.html#homePage", {transition: "slide"});
}



