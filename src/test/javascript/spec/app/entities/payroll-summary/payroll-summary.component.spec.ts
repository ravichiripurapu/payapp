/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';
import { Headers } from '@angular/http';

import { PayappTestModule } from '../../../test.module';
import { PayrollSummaryComponent } from '../../../../../../main/webapp/app/entities/payroll-summary/payroll-summary.component';
import { PayrollSummaryService } from '../../../../../../main/webapp/app/entities/payroll-summary/payroll-summary.service';
import { PayrollSummary } from '../../../../../../main/webapp/app/entities/payroll-summary/payroll-summary.model';

describe('Component Tests', () => {

    describe('PayrollSummary Management Component', () => {
        let comp: PayrollSummaryComponent;
        let fixture: ComponentFixture<PayrollSummaryComponent>;
        let service: PayrollSummaryService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PayappTestModule],
                declarations: [PayrollSummaryComponent],
                providers: [
                    PayrollSummaryService
                ]
            })
            .overrideTemplate(PayrollSummaryComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PayrollSummaryComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PayrollSummaryService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new PayrollSummary(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.payrollSummaries[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
