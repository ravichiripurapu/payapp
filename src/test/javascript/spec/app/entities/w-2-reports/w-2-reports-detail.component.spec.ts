/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';

import { PayappTestModule } from '../../../test.module';
import { W2ReportsDetailComponent } from '../../../../../../main/webapp/app/entities/w-2-reports/w-2-reports-detail.component';
import { W2ReportsService } from '../../../../../../main/webapp/app/entities/w-2-reports/w-2-reports.service';
import { W2Reports } from '../../../../../../main/webapp/app/entities/w-2-reports/w-2-reports.model';

describe('Component Tests', () => {

    describe('W2Reports Management Detail Component', () => {
        let comp: W2ReportsDetailComponent;
        let fixture: ComponentFixture<W2ReportsDetailComponent>;
        let service: W2ReportsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PayappTestModule],
                declarations: [W2ReportsDetailComponent],
                providers: [
                    W2ReportsService
                ]
            })
            .overrideTemplate(W2ReportsDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(W2ReportsDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(W2ReportsService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new W2Reports(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.w2Reports).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
