<template>
    <div class="card shadow"
         :class="type === 'dark' ? 'bg-default': ''">


        <div class="table-responsive">
            <base-table class="table align-items-center table-flush"
                        :class="type === 'dark' ? 'table-dark': ''"
                        :thead-classes="type === 'dark' ? 'thead-dark': 'thead-light'"
                        tbody-classes="list"
                        :data="tableData">
                <template slot="columns" style="font-size: 15px;">
                    <th :style="thStyle">일자</th>
                    <th :style="thStyle">이름</th>
                    <th :style="thStyle">내용</th>
                    <th :style="thStyle"></th>
                </template>

                <template slot-scope="{row}" style="width: 100%;">
                    <td class="name" style="font-size: 20px; cursor:pointer; width:15%;" @click.prevent="showModal(row)">
                        {{row.insertTime}}
                    </td>
                    <td class="name" style="font-size: 20px; cursor:pointer; width:15%;" @click.prevent="showModal(row)">
                        {{row.sendUserName}}
                        <a href="#" class="avatar avatar-sm rounded-circle">
                            <img alt="" :src="row.sendUserProfileUrl" style="width:90%;">
                        </a>
                    </td>
                    <td class="name overText" style="font-size: 15px; cursor:pointer; width:40%;" @click.prevent="showModal(row)">
                       <div class="name overText"  v-html="row.contents"> </div>
                    </td>

                    <td style="width:10%">
                        <div class="d-flex align-items-center" id="row.messageId">
                            <i v-if="row.isLike =='Y'" class="ni ni-favourite-28 " id="" style="color: pink;"></i>
                        </div>
                    </td>

                </template>

            </base-table>
        </div>

        <template>
            <!-- Modals -->
            <div class="col text-right">

                <modal :show.sync="modals.modal1">
                    <span class="ni ni-favourite-28 text-right cursor:pointer" v-bind:class="[modalData.isLike ? 'pink' : 'grey']" @click="toggleClass()"></span>
                    <div class="text-left">
                        <h2 slot="header" class="modal-title" id="modal-title-default">일자 : {{modalData.date}}</h2>

                    </div>
                    <br>
                    <div class="text-left">
                        <h2 slot="header" class="modal-title" id="modal-title-default1">이름 :  {{modalData.name}}</h2>
                    </div>
                    <br>

                    <div class="text-left" style="height: 100%">
                        <p class="description" v-html="modalData.contents"> </p>
                    </div>
                    <br>
                    <br>
                    <div class="text-right">
                        <base-button type="primary" @click="replyMessage(modalData.sendUserId)" >답장</base-button>
                        <base-button type="neutral" class="ml-auto" @click="modals.modal1 = false">취소
                        </base-button>
                    </div>
                </modal>

            </div>
        </template>

    </div>


</template>
<style>
    .pink{
        color: pink;
    }

    .grey{
        color: grey;
    }
</style>
<script>
    import Modal from "@/components/Modal.vue";
    var moment = require('moment');
    moment().format();

    let messages = [];
    let message = {messageId:0,isLike:"N"};
    export default {
        name: 'projects-table',
        components: {
            Modal
        },
        props: {
            type: {
                type: String
            },
            user: Object,
            date: String
        },
        computed: {
            thStyle() {
                return {
                    fontSize:'15px'
                };
            }
        },
        methods: {
            toggleClass: function(){
                // Check value
                if(this.modalData.isLike){
                    this.modalData.isLike = false;
                }else{
                    this.modalData.isLike = true;
                }
                this.tableData.forEach((msg, ind) => {
                    if(msg.messageId==this.modalData.messageId) {
                        this.tableData[ind].isLike = this.modalData.isLike?'Y':'N';
                    }
                })
                if (this.modalData.isLike) message.isLike="Y";
                else message.isLike="N";
                message.messageId = this.modalData.messageId;
                this.$http.post(`/message/like/`, message,
                    {
                        headers: {
                            Authorization: `Bearer ${this.user.token}`
                            ,'Content-Type':'application/json'
                        }
                    })
                    .then(res => {
                        console.log('message table  isLike update',res.data);

                    });

            },
            showModal: function(msg){
                this.modalData.messageId = msg.messageId;
                this.modalData.name = msg.sendUserName;
                this.modalData.contents = msg.contents;
                this.modalData.receivedUserId = msg.receivedUserId;
                if (msg.isLike == 'Y') this.modalData.isLike = true;
                else this.modalData.isLike = false;
                this.modalData.sendUserId = msg.sendUserId;
                this.modalData.date = msg.insertTime;

                this.modals.modal1 = true;
            },receiveMsg: function(sendUserId, user){
                this.tableData = [];

                if (sendUserId == 'all'){
                    this.$http.get(`/message/receive/user/`+user.userId,  { headers: { Authorization: `Bearer `+user.token } })
                        .then(res => {
                            res.data.messages.forEach(message =>{
                                message.contents = message.contents.replace(/(?:\r\n|\r|\n)/g, '<br />');
                                message.insertTime = moment(message.insertTime,"YYYY-MM-DDTHH:mm:ssZ").format('YYYY-MM-DD HH:mm');
                                this.tableData.push(message);
                            });

                        });
                }else if(sendUserId != 'all' && sendUserId != null) {
                    this.$http.get(`/message/send/user/`+user.userId+`/`+sendUserId,  { headers: { Authorization: `Bearer `+user.token } })
                        .then(res => {
                            res.data.messages.forEach(message =>{
                                message.contents = message.contents.replace(/(?:\r\n|\r|\n)/g, '<br />');
                                message.insertTime = moment(message.insertTime,"YYYY-MM-DDTHH:mm:ssZ").format('YYYY-MM-DD HH:mm');
                                this.tableData.push(message);
                            });

                        });
                }
            }, replyMessage: function (sendUserID) {
                console.log('[CHILD] Message Table  replyMessage Method : ', sendUserID);
                this.modals.modal1 = false;
                this.$parent.replyMessage(sendUserID);
            }
        },
        data() {
            return {
                isActive: true,
                tableData:messages,
                modals: {
                    modal1: false
                },
                modalData: {
                    date: null,
                    name: null,
                    contents: null,
                    isLike: null,
                    sendUserId: null,
                    messageId: null,
                    receivedUserId: null

                }
            }
        }, mounted () {


        }
    }
</script>
<style scoped>

    .overText {
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
        font-size: 20px;
        max-width: 600px;
    }


    body span {
        display: block;
    }
</style>
