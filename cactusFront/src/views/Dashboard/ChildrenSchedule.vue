<template>
<!--    <div>HEllo</div>-->
    <div class="card shadow" style="width:100%; height:100%;">
        <div class="card-header box2Header">
            <div class="row">
                <h3 class="mb-0 todaySchHeaderStr"><h1 class="ni ni-calendar-grid-58 todaySchHeaderStr"></h1>  오늘의 일정</h3>
            </div>
        </div>
        <div class="card-body">
            <div class="row">
                <vue-good-table
                        style="width:100%;"
                        :columns="periodHeader"
                        :rows="periodData"
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
                today: moment(new Date()).format('YYYYMMDD'),
                user: this.$store.state.user,
                parentData: [],
                periodHeader: [
                    {
                        field: 'schdId',
                        hidden: true,
                    },                    {
                        label: '이름',
                        field: 'userName',
                        width: '10%',
                    },
                    {
                        label: '시간',
                        field: 'schdTime',
                        type: 'time',
                        width: '10%',
                    },
                    {
                        label: '일정',
                        field: 'periodName',
                        width: '25%',
                    },
                    {
                        label: '장소',
                        field: 'place',
                        width: '35%',
                    },
                    {
                        label: '내용',
                        field: 'periodRemark',
                        width: '30%',
                    },
                ],
                periodData: [],
                modalShow: false,
            }
        },
        props: {
            // periodData: Array,
            date: String,
        },
        watch: {
            date: function() { // watch it
                this.findSchedule();
            }
        },
        created() {
            this.$http.get(`/user/parents/${this.user.userId}`,  { headers: { Authorization: `Bearer ${this.user.token}` } })
                .then(res => {
                    this.parentData = res.data.users;
                    this.findSchedule();
                });
        },
        methods: {
            findSchedule() {
                this.periodData = [];
                this.parentData.forEach(parent => {
                    this.$http.get(`/period/schedule/day/user/${this.today}/${parent.userId}`,  { headers: { Authorization: `Bearer ${this.user.token}` } })
                        .then(res => {
                            res.data.schedules.forEach(schedule =>{
                                schedule.userName = parent.name;
                                schedule.schdTime = moment(schedule.schdTime,"YYYY-MM-DDTHH:mm:ssZ").format('HH:mm');
                                this.periodData.push(schedule);
                            });

                        });
                });
            }
        }
    }
</script>

<style scoped>

</style>
