/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';
import { Headers } from '@angular/http';

import { PayappTestModule } from '../../../test.module';
import { PayrollFrequencyComponent } from '../../../../../../main/webapp/app/entities/payroll-frequency/payroll-frequency.component';
import { PayrollFrequencyService } from '../../../../../../main/webapp/app/entities/payroll-frequency/payroll-frequency.service';
import { PayrollFrequency } from '../../../../../../main/webapp/app/entities/payroll-frequency/payroll-frequency.model';

describe('Component Tests', () => {

    describe('PayrollFrequency Management Component', () => {
        let comp: PayrollFrequencyComponent;
        let fixture: ComponentFixture<PayrollFrequencyComponent>;
        let service: PayrollFrequencyService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PayappTestModule],
                declarations: [PayrollFrequencyComponent],
                providers: [
                    PayrollFrequencyService
                ]
            })
            .overrideTemplate(PayrollFrequencyComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PayrollFrequencyComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PayrollFrequencyService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new PayrollFrequency(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.payrollFrequencies[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
