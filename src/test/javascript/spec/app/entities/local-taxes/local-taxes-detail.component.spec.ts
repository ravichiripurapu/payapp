/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';

import { PayappTestModule } from '../../../test.module';
import { LocalTaxesDetailComponent } from '../../../../../../main/webapp/app/entities/local-taxes/local-taxes-detail.component';
import { LocalTaxesService } from '../../../../../../main/webapp/app/entities/local-taxes/local-taxes.service';
import { LocalTaxes } from '../../../../../../main/webapp/app/entities/local-taxes/local-taxes.model';

describe('Component Tests', () => {

    describe('LocalTaxes Management Detail Component', () => {
        let comp: LocalTaxesDetailComponent;
        let fixture: ComponentFixture<LocalTaxesDetailComponent>;
        let service: LocalTaxesService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PayappTestModule],
                declarations: [LocalTaxesDetailComponent],
                providers: [
                    LocalTaxesService
                ]
            })
            .overrideTemplate(LocalTaxesDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(LocalTaxesDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LocalTaxesService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new LocalTaxes(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.localTaxes).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
