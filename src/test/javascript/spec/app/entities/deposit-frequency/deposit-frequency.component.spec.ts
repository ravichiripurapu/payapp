/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';
import { Headers } from '@angular/http';

import { PayappTestModule } from '../../../test.module';
import { DepositFrequencyComponent } from '../../../../../../main/webapp/app/entities/deposit-frequency/deposit-frequency.component';
import { DepositFrequencyService } from '../../../../../../main/webapp/app/entities/deposit-frequency/deposit-frequency.service';
import { DepositFrequency } from '../../../../../../main/webapp/app/entities/deposit-frequency/deposit-frequency.model';

describe('Component Tests', () => {

    describe('DepositFrequency Management Component', () => {
        let comp: DepositFrequencyComponent;
        let fixture: ComponentFixture<DepositFrequencyComponent>;
        let service: DepositFrequencyService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PayappTestModule],
                declarations: [DepositFrequencyComponent],
                providers: [
                    DepositFrequencyService
                ]
            })
            .overrideTemplate(DepositFrequencyComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DepositFrequencyComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DepositFrequencyService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new DepositFrequency(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.depositFrequencies[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
