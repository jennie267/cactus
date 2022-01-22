<template>
    <div>
        <base-header type="gradient-success" class="pt-5">
            <h1 class="" ><h1 class="ni ni-tv-2"></h1>  {{user.name}}님의 대시보드</h1>
            <hr>
        </base-header>
       <div class="grid2x2 ">
            <div class="box box1 card">
                <div>
                    <FullCalendar
                            defaultView="dayGridMonth"
                            ref="fullCalendar"
                            :plugins="calendarPlugins"
                            locale="ko"
                            :weekends="true"
                            :header="{
                                left: '',
                                center: 'title',
                                right: ''
                        }"
                            @dateClick="dateClick"

                    />
                    <!--                            :selectable="true"-->
<!--                    :events="[
                    { title: 'event 1', date: '2019-12-22' },
                    { title: 'event 2', date: '2019-12-23' }
                    ]"-->
<!--
                    left: 'prev',
                    center: 'title',
                    right: 'next'-->
                </div>
            </div>
            <div class="box box2">
                <div>
                    <parents-schedule v-if="user.type === 'PARENT'" style="width:100%; height:100%;" :date="date"></parents-schedule>
                    <children-schedule v-if="user.type === 'CHILD'" style="width:100%; height:100%;" :date="date"></children-schedule>
                </div>
                <br>
                <div>
                    <today-messages style="width:100%; height:100%;" :date="date"></today-messages>
                </div>
            </div>
            <div class="box box3">
                <div>
                    <parents-table v-if="user.type === 'CHILD'" @selectUser="selectUser" style="width:100%; height:100%;"></parents-table>
                    <children-table v-if="user.type === 'PARENT'" @selectUser="selectUser" style="width:100%; height:100%;"></children-table>
                </div>
            </div>
            <div class="box box4">
                <div>
                    <dashboard-profile :chkUser="selectedUser" :user="user" style="width:100%; height:100%;"></dashboard-profile>
                </div>
            </div>
           <div class="box box5">
               <div>
                   <write-letter ref="letterModal" :revUser="selectedUser" :user="user" style="width:100%; height:100%;"></write-letter>
               </div>
           </div>

        </div>
    </div>
</template>
<script>
import FullCalendar from '@fullcalendar/vue'
import dayGridPlugin from '@fullcalendar/daygrid'
import interactionPlugin from '@fullcalendar/interaction'
import DashboardProfile from './Dashboard/DashboardProfile'
import ParentsTable from './Dashboard/ParentsTable'
import ChildrenTable from './Dashboard/ChildrenTable'
import ParentsSchedule from './Dashboard/ParentsSchedule'
import ChildrenSchedule from './Dashboard/ChildrenSchedule'
import TodayMessages from './Dashboard/TodayMessages'
import WriteLetter from './Dashboard/WriteLetter'
import dateUtil from '../common/dateUtil'
  export default {
    components: {
        FullCalendar,
        DashboardProfile,
        ParentsTable,
        ChildrenTable,
        ParentsSchedule,
        ChildrenSchedule,
        TodayMessages,
        WriteLetter
    },
    data() {
      return {
          user: this.$store.state.user,
          calendarPlugins: [ dayGridPlugin
              , interactionPlugin
          ],
          date: dateUtil.getToday(),
          selectedUser: {},
          periodHeader: [
              {
                  field: 'schdId',
                  hidden: true,
              },
              {
                  label: '담당자',
                  field: 'manager',
                  width: '12%',
              },
              {
                  label: '위치',
                  field: 'place',
                  width: '25%',
              },
              {
                  label: '시간',
                  field: 'schdTime',
                  type: 'date',
                  dateInputFormat: `yyyy-MM-dd'T'HH:mm:ss`,
                  dateOutputFormat: 'HH:mm:ss',
                  width: '10%',
              },
              {
                  label: '내용',
                  field: 'periodRemark',
                  width: '25%',
              },
          ],
          periodData: [],
      }
    },
    methods: {
        dateClick(info) {
            this.$router.push({name:"schedule", params:{requestDate:info.dateStr}});
        },
        selectUser(user) {
            this.selectedUser = user
        },
    },
      mounted() {
          this.$http.get(`/period/schedule/day/user/${this.date.replace(/-/gi,'')}/${this.user.userId}`,  { headers: { Authorization: `Bearer ${this.user.token}` } })
              .then(res => {
                  this.periodData = res.data.schedules;
              });
      }
  };
</script>
<style>
    @import '~@fullcalendar/core/main.css';
    @import '~@fullcalendar/daygrid/main.css';

    html, body {
        height: 100%;
        margin: 0;
    }
    .grid2x2 {
        min-height: 100%;
        display: flex;
        flex-wrap: wrap;
        flex-direction: row;
    }
    .grid2x2 > div {
/*        display: flex;
        flex-basis: calc(50% - 40px);
        !*justify-content: center;*!
        flex-direction: column;*/
    }
    .grid2x2 > div > div {
        display: flex;
        justify-content: center;
        flex-direction: row;
    }

    .box { margin: 20px;
        display: flex;
        flex-basis: calc(50% - 40px);
        /*justify-content: center;*/
        flex-direction: column;
    }
    .box1 {
        background-color: white;
        flex-basis: calc(40% - 40px);
        height: max-content;
    }
    .box2 {
        flex-basis: calc(60% - 40px);
    }
    .box3 {
        flex-basis: calc(20% - 40px);
    }
    .box4 {
        flex-basis: calc(35% - 40px);
    }
    .box5 {
        flex-basis: calc(45% - 40px);
    }
    .box2Header {
        background-color: #3a6fa0;
    }
    .box22Header {
        background-color: #d18392;
    }
    .box3Header {
        background-color: #71b284;
    }
    .box4Header {
        background-color: #727bae;
    }
    .box5Header {
        background-color: #b06e6c;
    }

    .todaySchHeader {
        background-color: #1e2b37;
    }
    .todaySchHeaderStr {
        color: #eff2f7;
    }


</style>
