/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';
import { Headers } from '@angular/http';

import { PayappTestModule } from '../../../test.module';
import { AnnualReportsComponent } from '../../../../../../main/webapp/app/entities/annual-reports/annual-reports.component';
import { AnnualReportsService } from '../../../../../../main/webapp/app/entities/annual-reports/annual-reports.service';
import { AnnualReports } from '../../../../../../main/webapp/app/entities/annual-reports/annual-reports.model';

describe('Component Tests', () => {

    describe('AnnualReports Management Component', () => {
        let comp: AnnualReportsComponent;
        let fixture: ComponentFixture<AnnualReportsComponent>;
        let service: AnnualReportsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PayappTestModule],
                declarations: [AnnualReportsComponent],
                providers: [
                    AnnualReportsService
                ]
            })
            .overrideTemplate(AnnualReportsComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AnnualReportsComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AnnualReportsService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new AnnualReports(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.annualReports[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
