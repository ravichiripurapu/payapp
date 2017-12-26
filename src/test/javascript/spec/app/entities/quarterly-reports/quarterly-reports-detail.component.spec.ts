/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';

import { PayappTestModule } from '../../../test.module';
import { QuarterlyReportsDetailComponent } from '../../../../../../main/webapp/app/entities/quarterly-reports/quarterly-reports-detail.component';
import { QuarterlyReportsService } from '../../../../../../main/webapp/app/entities/quarterly-reports/quarterly-reports.service';
import { QuarterlyReports } from '../../../../../../main/webapp/app/entities/quarterly-reports/quarterly-reports.model';

describe('Component Tests', () => {

    describe('QuarterlyReports Management Detail Component', () => {
        let comp: QuarterlyReportsDetailComponent;
        let fixture: ComponentFixture<QuarterlyReportsDetailComponent>;
        let service: QuarterlyReportsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PayappTestModule],
                declarations: [QuarterlyReportsDetailComponent],
                providers: [
                    QuarterlyReportsService
                ]
            })
            .overrideTemplate(QuarterlyReportsDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(QuarterlyReportsDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(QuarterlyReportsService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new QuarterlyReports(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.quarterlyReports).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
