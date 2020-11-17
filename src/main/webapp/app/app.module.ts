import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { TestjioApplicationSharedModule } from 'app/shared/shared.module';
import { TestjioApplicationCoreModule } from 'app/core/core.module';
import { TestjioApplicationAppRoutingModule } from './app-routing.module';
import { TestjioApplicationHomeModule } from './home/home.module';
import { TestjioApplicationEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ActiveMenuDirective } from './layouts/navbar/active-menu.directive';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    TestjioApplicationSharedModule,
    TestjioApplicationCoreModule,
    TestjioApplicationHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    TestjioApplicationEntityModule,
    TestjioApplicationAppRoutingModule,
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, ActiveMenuDirective, FooterComponent],
  bootstrap: [MainComponent],
})
export class TestjioApplicationAppModule {}
