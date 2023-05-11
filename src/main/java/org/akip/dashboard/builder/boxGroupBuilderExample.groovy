package org.akip.dashboard.builder

def hqlTravelPlansCreated = '''
   select travelPlanProcess.travelPlan
   from TravelPlanProcess travelPlanProcess
   where travelPlanProcess.processInstance.startDate between :startDate and :endDate
'''

def travelPlans = entityManager
.createQuery(hqlTravelPlansCreated)
  .setParameter('startDate', dashboardRequest.startDateTime)
  .setParameter('endDate', dashboardRequest.endDateTime)
					.resultList

def prices = travelPlans.findAll{ it.price }.collect{ it.price }
def averagePrice = (prices) ? (prices.sum() / prices.size()) : 0
def numPax = travelPlans.findAll{ it.numPax }.collect{ it.numPax }
def averageNumPax = (numPax) ? (numPax.sum() / numPax.size()) : 0

return [
  [
    title: 'Plans Created',
    value: "${travelPlans.size()}"
  ],
  [
    title: 'Average Price',
    value: "R\$ ${averagePrice}"
  ],
  [
    title: 'Average Num Pax',
    value: "${averageNumPax}"
  ]
]
