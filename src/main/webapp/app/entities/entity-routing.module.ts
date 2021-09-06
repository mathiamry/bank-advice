import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'agency',
        data: { pageTitle: 'bankAdviceApp.agency.home.title' },
        loadChildren: () => import('./agency/agency.module').then(m => m.AgencyModule),
      },
      {
        path: 'enterprise',
        data: { pageTitle: 'bankAdviceApp.enterprise.home.title' },
        loadChildren: () => import('./enterprise/enterprise.module').then(m => m.EnterpriseModule),
      },
      {
        path: 'advisor',
        data: { pageTitle: 'bankAdviceApp.advisor.home.title' },
        loadChildren: () => import('./advisor/advisor.module').then(m => m.AdvisorModule),
      },
      {
        path: 'manager',
        data: { pageTitle: 'bankAdviceApp.manager.home.title' },
        loadChildren: () => import('./manager/manager.module').then(m => m.ManagerModule),
      },
      {
        path: 'appointment',
        data: { pageTitle: 'bankAdviceApp.appointment.home.title' },
        loadChildren: () => import('./appointment/appointment.module').then(m => m.AppointmentModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
