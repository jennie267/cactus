<template>
    <div>
        <base-header type="gradient-success" class="pb-5">
            <div class="card-header bg-transparent row align-items-center">
                <div class="main">
                    <h1 class=""><h1 class="ni ni-square-pin"></h1>  부모님 위치</h1>
                </div>
            </div>
        </base-header>
        <div class="row">
            <div class="tot">
                <div class="rightOne">
                    <base-dropdown >
                        <base-button slot="title"  class="dropdown-toggle" style="background-color: white; color: #172b4d">
                            {{selectedParent.name}}
                        </base-button>
                        <a class="dropdown-item" @click="selectedParent=parent" v-for="parent in parents" :key="parent.id">{{parent.name}}</a>
                    </base-dropdown>
                </div>
                <div class="rightTwo">
                    <base-input class="" addon-left-icon="ni ni-calendar-grid-58" >
                        <flat-pickr slot-scope="{focus, blur}"
                                    @on-open="focus"
                                    @on-close="blur"
                                    :config="{allowInput: true}"
                                    class="form-control datepicker"
                                    v-model="date">
                        </flat-pickr>
                    </base-input>
                </div>
                <div class="rightThree">
                    <base-button @click="getLocation">검색</base-button>
                </div>
            </div>
        </div>

            <div class="">
                        <div class="card-body">
                            <div class="row">
                                <gmap-map
                                        :center=center
                                        :zoom=zoom
                                        :map-type-id=mapType
                                        style="width: 100%; height: 650px"
                                        ref="map"
                                        @dragend="center=reportedCenter"
                                        @zoom_changed="update('zoom', $event)"
                                        @center_changed="update('reportedCenter', $event)"
                                        :options="{
                               zoomControl: true,
                               mapTypeControl: false,
                               scaleControl: false,
                               streetViewControl: false,
                               rotateControl: false,
                               fullscreenControl: true,
                               disableDefaultUi: false
                            }">
                                    <gmap-marker v-for="m in markers" :key="m" :position="m.position" :opacity="1">
                                        <gmap-info-window :opened="m.ifw">{{m.ifw2text}}</gmap-info-window>
                                    </gmap-marker>
                                    <gmap-polyline :path="plPath" :draggable="false" :options="{geodesic:true, strokeColor:'#FF0000'}">
                                    </gmap-polyline>
                                </gmap-map>
                            </div>
                        </div>
                </div>
    </div>
</template>
<script>
import flatPickr from 'vue-flatpickr-component';
import dateUtil from '../common/dateUtil';
var moment = require('moment');
moment().format();

  export default {
      components: {
          flatPickr
      },
      data() {
          return {
              user: this.$store.state.user,
              date: dateUtil.getToday(),
              center: {
                  lat: 37.48877560436582,
                  lng: 127.00939031639054
              },
              reportedCenter: {
                  lat: 37.48877560436582,
                  lng: 127.00939031639054
              },
              zoom: 20,
              mapType: 'roadmap',
              markers: [],
              plPath: [],
              selectedParent: {
                  "userId": 0,
                  name: '선택'
              },
              defaultParent: {
                  "userId": 0,
                  name: '선택'
              },
              parents: []
          }
      },
      methods: {
          update(field, event) {
              if (field === 'reportedCenter') {
                  this.reportedCenter = {
                      lat: event.lat(),
                      lng: event.lng(),
                  };
              } else if (field === 'bounds') {
                  this.mapBounds = event;
              } else {
                  this.$set(this, field, event);
              }
          },
          getLocation: function(){
              if(this.selectedParent.userId>0){
                  this.$http.get('/location/footprints/user/date/'+this.selectedParent.userId+'/'+this.date.replace(/-/gi,''), { headers: { Authorization: `Bearer ${this.user.token}` } })
                      .then((result) => {
                          this.markers = [];
                          let footPrints = result.data.footPrints;
                          let marker;
                          footPrints.forEach((footPrint) => {
                              footPrint.insertTime = moment(footPrint.insertTime,"YYYY-MM-DDTHH:mm:ssZ").format('HH:mm:ss');
                              marker = {
                                  position: {
                                      lat: footPrint.latitude,
                                      lng: footPrint.longitude
                                  },
                                  ifw: true,
                                  ifw2text: footPrint.insertTime
                              };
                              this.markers.push(marker);
                          });
                          this.plPath = this.markers.map(value => value.position);
                          if(this.plPath.length>0){
                            this.center = this.plPath[this.plPath.length-1];
                          }
                          this.reportedCenter = this.center;
                          this.title = this.date + ' ' + this.selectedParent.name + '님의 경로';
                      })
              }
          }
      },
      mounted() {
          this.$http.get(`/user/parents/`+this.user.userId,  { headers: { Authorization: `Bearer `+this.user.token } })
              .then(res => {
                  this.parents = res.data.users;
                  if (this.parents.length > 0){
                      this.selectedParent.name = this.parents[0].name;
                      this.selectedParent.userId = this.parents[0].userId;
                      this.getLocation();
                  }

              });

      }
  }
</script>
<style>
    .main {
        float: left;
    }

    .tot {
        margin-left: 3%;
        width: 63%;
        float: right;
        /*margin: 10px auto;*/
    }

    .rightOne {
        /*border: 1px solid red;*/
        float: left;
        width:113px;
        /*box-sizing: border-box;*/

    }

    .rightTwo {
        /*border: 1px solid green;*/
        float: left;
        width:30%;
        /*box-sizing: border-box;*/
    }

    .rightThree {
        /*border: 1px solid blue;*/
        float: left;
        width: 10%;
        /*box-sizing: border-box;*/
    }
</style>
