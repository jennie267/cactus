<template>
    <div>
        <base-header type="gradient-success" class="pt-5">
            <h1 class="" v-if="user.type==='PARENT'"><h1 class="ni ni-calendar-grid-58"></h1>  {{user.name}}님의 일정</h1>
            <h1 class="" v-if="user.type==='CHILD' && selectedParent!=null"><h1 class="ni ni-calendar-grid-58"></h1>  {{selectedParent.name}}님의 일정</h1>
            <h1 class="" v-if="user.type==='CHILD' && selectedParent==null"><h1 class="ni ni-calendar-grid-58"></h1>  부모님 일정</h1>
            <hr>
        </base-header>
        <div v-if="user.type==='CHILD'" class="col-sm-3" style="margin-left: 3%; margin-bottom: 1%">
            <multiselect v-model="selectedParent" track-by="name" label="name" placeholder="부모님선택" :options="this.$store.state.parents" :searchable="false" :allow-empty="true">
                <template slot="singleLabel" slot-scope="{ option }"><strong>{{ option.name }}</strong></template>
            </multiselect>
        </div>
        <div class="container-fluid">
            <div class="row">
                <div class="col">
                    <div class="card">
                        <div class="card-body">
                            <div class="row">
                                <div class="col-md-7">
                                    <FullCalendar
                                        defaultView="dayGridMonth"
                                        :plugins="calendarPlugins"
                                        locale="ko"
                                        :weekends="true"
                                        :header="{
                                            left: 'prev',
                                            center: 'title',
                                            right: 'next'
                                        }"
                                        :events="events"
                                        :selectable="true"
                                        @dateClick="dateClick"
                                        @eventClick="eventClick"
                                        @datesRender="saveFromTo"
                                    />
                                </div>
                                <div class="col-md-5">
                                    <div class="row mt-5">
                                        <!--<div class="row mt-5" v-if="isStatus">-->
                                        <div class="col">
                                            <dailySchedule-table ref="dailyTable" :selectedParent="selectedParent" title="Light Table"></dailySchedule-table>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>
<script>
import FullCalendar from '@fullcalendar/vue'
import Multiselect from 'vue-multiselect'
import dayGridPlugin from '@fullcalendar/daygrid'
import interactionPlugin from '@fullcalendar/interaction'
import DailyScheduleTable from './Tables/DailyScheduleTable'

var moment = require('moment');
moment().format();

export default {
    name: 'schedule',
  components: {
    FullCalendar // make the <FullCalendar> tag available
    , DailyScheduleTable
      ,Multiselect
  },
  data() {
    return {
        user: this.$store.state.user,
        calendarPlugins: [ dayGridPlugin
                       , interactionPlugin
        ],
        // isStatus: false,
        name:'',
        today:'',
        schedule:'',
        selectedParent: this.$store.state.user.type==='CHILD'?null:this.$store.state.user,
        selectedDate: '',
        fromDate: '',
        toDate: '',
        events: [],
        bcolor:['#ba6270','#6287ba','#6672a8','#fff1ab','#ecbbb7','#5555aa','#c1ecb7','#8aba62','#9fd6fa','#62baa4'],
        fcolor:['#e9ecef','#e9ecef','#e9ecef','#172b4d','#172b4d','#e9ecef','#172b4d','#e9ecef','#172b4d','#e9ecef'],
        isEnter: true,
    }
  },
    watch: {
        fromDate: function(){this.findEvents()},
        selectedParent: function(){this.findEvents()},
        getDeletedSchdId (val) {
            this.deleteEventsBySchdId(val);
        },
        getDeletedPeriodId (val) {
            this.deleteEventsByPeriodId(val);
        },
        getUpdatedPeriodId (val) {
            this.updateEventsByPeriodId(val);
        },
        getInsertedPeriodId (val) {
            this.insertEventsByPeriodId(val);
        },
    },
    computed: {
        getDeletedSchdId () {
            return this.$store.getters.getDeletedSchdId;
        },
        getDeletedPeriodId () {
            return this.$store.getters.getDeletedPeriodId;
        },
        getUpdatedPeriodId () {
            return this.$store.getters.getUpdatedPeriodId;
        },
        getInsertedPeriodId () {
            return this.$store.getters.getInsertedPeriodId;
        },
    },
  methods: {
    dateClick(info) {
        this.selectedDate = info.dateStr;
        this.$refs.dailyTable.date = info.dateStr;
        this.$refs.dailyTable.tableData = [];
        this.events.forEach(event => {
            if(moment(event.schedule.schdTime,"YYYY-MM-DDTHH:mm:ssZ").format('YYYY-MM-DD') === info.dateStr) {
                event.schedule.formattedTime = moment(event.schedule.schdTime,"YYYY-MM-DDTHH:mm:ssZ").format('HH:mm');
                this.$refs.dailyTable.tableData.push(event.schedule);
            }
        });
    },
      saveFromTo: function(arg) {
          this.fromDate = moment(arg.view.activeStart).format('YYYYMMDD');
          this.toDate = moment(arg.view.activeEnd).format('YYYYMMDD');
      },
      findEvents: function() {
          this.events = [];
          if(this.user.type==='PARENT'){
              this.getEvents(this.fromDate, this.toDate, this.$store.state.user.userId);
          }
          else if(this.user.type==='CHILD'){
              if(this.selectedParent!=null){
                  this.getEvents(this.fromDate, this.toDate, this.selectedParent.userId);
              }
              else{
                  this.$store.state.parents.forEach(parent => {
                      this.getEvents(this.fromDate, this.toDate, parent.userId);
                  })
              }
          }
      },
      getEvents: function(fromDate, toDate, userId) {
          this.$http.get(`/period/schedule/from/to/user/order/${fromDate}/${toDate}/${userId}`,  { headers: { Authorization: `Bearer `+this.user.token } })
              .then(res => {
                  let i = 0;
                  let st = 0;
                  res.data.schedules.forEach(schedule =>{
                      let event = {}
                      if (st != schedule.periodId) {
                          st = schedule.periodId;
                          if (i == 9) i=0;
                          else i++;
                      }

                      event.schedule = schedule;
                      event.color = this.bcolor[i];
                      event.textColor = this.fcolor[i];
                      event.title = schedule.periodName;
                      event.date = moment(schedule.schdTime,"YYYY-MM-DDTHH:mm:ssZ").format('YYYY-MM-DD');
                      this.events.push(event);
                  });
                  if(this.isEnter) {
                      this.dateClick({dateStr: this.$moment(new Date()).format('YYYY-MM-DD')});
                      this.isEnter = false;
                  }
              });
      },
    eventClick (eventArg) {
        this.dateClick({dateStr: moment(eventArg.event.extendedProps.schedule.schdTime,"YYYY-MM-DDTHH:mm:ssZ").format('YYYY-MM-DD')});
    },
      deleteEventsBySchdId(schdId) {
        if(schdId===0) return;
        this.events.forEach((event, index) => {
            if(event.schedule.schdId===schdId) {
                this.events.splice(index, 1);
            }
        });
        this.dateClick({dateStr: this.selectedDate});
        this.$store.commit('setDeletedSchdId', 0);
      },
      deleteEventsByPeriodId(periodId) {
          if(periodId===0) return;
          this.events.forEach((event, index) => {
              if(event.schedule.periodId===periodId) {
                  this.events.splice(index, 1);
              }
          });
          this.dateClick({dateStr: this.selectedDate});
          this.$store.commit('setDeletedPeriodId', 0);
      },
      updateEventsByPeriodId(periodId) {
          if(periodId===0) return;
          let color;
          let textColor;
          this.events = this.events.filter(function(event){
              if(event.schedule.periodId===periodId) {
                  color = event.color;
                  textColor = event.textColor;
              }
              return event.schedule.periodId!==periodId;
          });
          this.$http.get(`/period/schedule/from/to/period/${this.fromDate}/${this.toDate}/${periodId}`,  { headers: { Authorization: `Bearer `+this.user.token } })
              .then(res => {
                  res.data.schedules.forEach(schedule =>{
                      let event = {}
                      event.schedule = schedule;
                      event.color = color;
                      event.textColor = textColor;
                      event.title = schedule.periodName;
                      event.date = moment(schedule.schdTime,"YYYY-MM-DDTHH:mm:ssZ").format('YYYY-MM-DD');
                      this.events.push(event);
                  });
                  this.dateClick({dateStr: this.selectedDate});
                  this.$store.commit('setUpdatedPeriodId', 0);
              });
      },
      insertEventsByPeriodId(periodId) {
          if(periodId===0) return;
          this.$http.get(`/period/schedule/from/to/period/${this.fromDate}/${this.toDate}/${periodId}`,  { headers: { Authorization: `Bearer `+this.user.token } })
              .then(res => {
                  let i = 0;
                  let st = 0;
                  res.data.schedules.forEach(schedule =>{
                      let event = {}
                      if (st != schedule.periodId) {
                          st = schedule.periodId;
                          if (i == 9) i=0;
                          else i++;
                      }

                      event.schedule = schedule;
                      event.color = this.bcolor[i];
                      event.textColor = this.fcolor[i];
                      event.title = schedule.periodName;
                      event.date = moment(schedule.schdTime,"YYYY-MM-DDTHH:mm:ssZ").format('YYYY-MM-DD');
                      this.events.push(event);
                  });
                  this.dateClick({dateStr: this.selectedDate});
                  this.$store.commit('setUpdatedPeriodId', 0);
              });
      },
  }, mounted() {
        if(this.user.type==='CHILD' && this.$store.state.parents.length===0) {
            this.$http.get(`/user/parents/`+this.user.userId,  { headers: { Authorization: `Bearer `+this.user.token } })
                .then(res => {
                    this.$store.state.parents = res.data.users;
                    this.findEvents();
                });
        }
    }
}
</script>
<style lang='scss'>

@import '~@fullcalendar/core/main.css';
@import '~@fullcalendar/daygrid/main.css';

</style>
