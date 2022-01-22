<template>
<!--    <div>HEllo</div>-->
    <div class="card shadow" style="width:100%; height:100%;">
        <div class="card-header box22Header">
            <div class="row">
                <h3 class="mb-0 todaySchHeaderStr"><h1 class="ni ni-email-83 todaySchHeaderStr"></h1>  오늘의 쪽지</h3>
            </div>
        </div>
        <div class="card-body">
            <div class="row">
                <vue-good-table
                        style="width:100%;"
                        :columns="messageHeader"
                        :rows="messageData"
                />
            </div>
        </div>
    </div>
</template>

<script>
import 'vue-good-table/dist/vue-good-table.css'
import { VueGoodTable } from 'vue-good-table/src'
var moment = require('moment');
moment().format();

    export default {
        components: {
            VueGoodTable,
        },
        data() {
            return {
                user: this.$store.state.user,
                messageHeader: [
                    {
                        label: '시간',
                        field: 'insertTime',
                        type: 'time',
                        width: '10%',
                    },
                    {
                        label: '보낸사람',
                        field: 'sendUserName',
                        width: '15%',
                    },
                    {
                        label: '내용',
                        field: 'contents',
                        width: '75%',
                    },

                ],
                messageData: [],
                modalShow: false,
            }
        },
        props: {
            // periodData: Array,
            date: String,
        },
        watch: {
            date: function() { // watch it
                this.findMessages();
            }
        },
        created() {
            this.findMessages();

        },
        methods: {
            findMessages() {
                this.$http.get(`/message/today/${this.user.userId}`,  { headers: { Authorization: `Bearer ${this.user.token}` } })
                    .then(res => {
                       res.data.messages.forEach(message =>{
                            message.insertTime = moment(message.insertTime,"YYYY-MM-DDTHH:mm:ssZ").format('HH:mm');
                            this.messageData.push(message);
                        });
                    });
            }
        }
    }
</script>

<style>

</style>
